package com.mirishop.userservice.application.service;

import static com.mirishop.userservice.common.constants.UserConstants.DEFAULT_PROFILE_IMAGE_PATH;

import com.mirishop.userservice.common.exception.CustomException;
import com.mirishop.userservice.common.exception.ErrorCode;
import com.mirishop.userservice.api.dto.request.ChangePasswordRequest;
import com.mirishop.userservice.api.dto.request.UserJoinRequest;
import com.mirishop.userservice.api.dto.request.UserUpdateRequest;
import com.mirishop.userservice.api.dto.response.UserJoinResponse;
import com.mirishop.userservice.domain.user.UserEntity;
import com.mirishop.userservice.domain.user.UserRepository;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final ImageUploadService imageUploadService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    /**
     * 유저 등록하는 메소드
     *
     * @param userJoinRequest 회원 가입 요청 정보 (이메일, 비밀번호, 닉네임, 자기소개)
     * @param imageFile       프로필 이미지 파일
     * @return UserJoinResponse 등록된 회원 정보 응답
     */
    @Override
    @Transactional
    public UserJoinResponse register(UserJoinRequest userJoinRequest, MultipartFile imageFile) throws IOException {
        // 유저 form 데이터 검증
        validatedDuplicatedEmail(userJoinRequest.getEmail());

        // 유저 ID UUID 생성 및 비밀번호 암호화 저장
        String userId = UUID.randomUUID().toString();
        String encodePassword = encodePassword(userJoinRequest.getPassword());

        // 프로필 이미지 처리
        String imagePath = imageUploadService.uploadTempImage(imageFile);
        String finalImagePath = "";
        if (!imagePath.equals(DEFAULT_PROFILE_IMAGE_PATH)) {
            finalImagePath = imageUploadService.confirmImageUpload(imagePath, userId);
        }

        // 엔티티 생성 후 저장
        UserEntity userEntity = userJoinRequest.toEntity(userId, encodePassword, finalImagePath);
        UserEntity savedUserEntity = userRepository.save(userEntity);

        return UserJoinResponse.from(savedUserEntity);
    }

    /**
     * 유저 업데이트 하는 메소드
     *
     * @param userUpdateRequest 유저 수정 정보 (닉네임, 프로필이미지, 자기소개)
     * @param userId            유저 Id
     */
    @Override
    @Transactional
    public void update(UserUpdateRequest userUpdateRequest, MultipartFile imageFile, String userId) throws IOException {
        // 유저 정보 확인
        UserEntity userEntity = userRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String newImagePath = userEntity.getProfileImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            String tempImagePath = imageUploadService.uploadTempImage(imageFile);
            newImagePath = imageUploadService.confirmImageUpload(tempImagePath, userId);
        }

        userEntity.updateUsername(userUpdateRequest.getUsername());
        userEntity.updateProfileImage(newImagePath);
        userEntity.updateBio(userUpdateRequest.getBio());
    }

    /**
     * 유저가 비밀번호 업데이트하는 메소드
     *
     * @param changePasswordRequest 비밀번호 변경 정보(기존/수정 후)
     * @param userId            유저 Id
     */
    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest, String userId) {
        // 유저 정보 확인
        UserEntity userEntity = userRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String storedPassword = userEntity.getPassword();
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();

        // 기존 비밀번호 검증 로직
        if (!isMatchesPassword(oldPassword, storedPassword)) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        // 새로운 비밀번호가 기존 비밀번호와 같은 경우
        if (isMatchesPassword(newPassword, storedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        userEntity.updatePassword(encodePassword(newPassword));
    }

    private void validatedDuplicatedEmail(String email) {
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        });
    }

    private String encodePassword(String password) {
        return bcryptPasswordEncoder.encode(password);
    }

    private boolean isMatchesPassword(String password, String storedPassword) {
        return bcryptPasswordEncoder.matches(password, storedPassword);
    }
}
