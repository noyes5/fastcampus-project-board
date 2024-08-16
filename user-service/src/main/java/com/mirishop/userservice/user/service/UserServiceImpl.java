package com.mirishop.userservice.user.service;

import com.mirishop.userservice.auth.domain.UserDetailsImpl;
import com.mirishop.userservice.common.exception.CustomException;
import com.mirishop.userservice.common.exception.ErrorCode;
import com.mirishop.userservice.user.domain.Role;
import com.mirishop.userservice.user.dto.ChangePasswordRequest;
import com.mirishop.userservice.user.dto.UserJoinResponse;
import com.mirishop.userservice.user.dto.UserRequest;
import com.mirishop.userservice.user.dto.UserUpdateRequest;
import com.mirishop.userservice.user.entity.User;
import com.mirishop.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mirishop.userservice.common.constants.UserConstants.EMAIL_REGEX;
import static com.mirishop.userservice.common.constants.UserConstants.USER_PASSWORD_LENGTH;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // 기본 이미지 경로는 추후 업로드 방식이 변경되면 수정 필요
    private static final String DEFAULT_PROFILE_IMAGE_PATH = "/uploads/images/default.jpg";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * MemberRequest를 받아 회원을 등록하는 메소드
     * MemberRequest = email, password, nickname, profileImage, bio
     */
    @Override
    @Transactional
    public UserJoinResponse register(final UserRequest userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        String profileImagePath = userRequest.getProfileImage();

        validateEmail(email);
        validatePassword(password);
        String profileImage = validateUploadProfileImage(profileImagePath);

        String encodedPassword = encodePassword(password);

        final User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(userRequest.getNickname())
                .profileImage(profileImage)
                .bio(userRequest.getBio())
                .role(Role.ROLE_USER) // role 설정
                .isDeleted(false) // 기본값 false
                .build();

        final User savedUser = userRepository.save(user);

        return new UserJoinResponse(savedUser);
    }

    /**
     * 유저 정보와 수정 정보(MemberUpdateRequest)를 받아 업데이트하는 메소드
     * MemberUpdateRequest = nickname, profileImage, bio
     */
    @Override
    @Transactional
    public void update(UserUpdateRequest userUpdateRequest, UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String nickname = userUpdateRequest.getNickname();
        String profileImagePath = userUpdateRequest.getProfileImage();
        String bio = userUpdateRequest.getBio();

        String profileImage = validateUploadProfileImage(profileImagePath);

        user.updateNickname(nickname);
        user.updateProfileImage(profileImage);
        user.updateBio(bio);

        userRepository.save(user);
    }

    /**
     * 유저 정보와 비밀번호 정보(ChangePasswordRequest)를 받아 비밀번호를 업데이트하는 메소드
     * ChangePasswordRequest = oldPassword, newPassword
     * 토큰만으로 유저 검증은 가능하지만 본인확인 보안 차원으로 oldPasswor를 한번더 받는다.
     */
    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest, UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        String storedPassword = user.getPassword();
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        validatePassword(newPassword);

        // 기존 비밀번호 검증 로직
        if (!isMatchesPassword(oldPassword, storedPassword)) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        // 새로운 비밀번호가 기존 비밀번호와 같은 경우
        if (isMatchesPassword(newPassword, storedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        user.updatePassword(encodePassword(newPassword));
        userRepository.save(user);
    }

    private void validateEmail(final String email) {
        validatedEmailForm(email);
        validatedDuplicatedEmail(email);
    }

    private void validatedEmailForm(final String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_FROM);
        }
    }

    private void validatedDuplicatedEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(existingUser -> {
                    throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
                });
    }

    private void validatePassword(String password) {
        if (password.length() < USER_PASSWORD_LENGTH) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD_LENGTH);
        }
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private String validateUploadProfileImage(String profileImagePath) {
        if (profileImagePath == null || profileImagePath.isEmpty()) {
            profileImagePath = DEFAULT_PROFILE_IMAGE_PATH;
        }
        return profileImagePath;
    }

    private boolean isMatchesPassword(String password, String storedPassword) {
        return bCryptPasswordEncoder.matches(password, storedPassword);
    }
}
