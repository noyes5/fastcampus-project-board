package com.mirishop.user.member.service;

import static com.mirishop.user.user.common.constants.UserConstants.EMAIL_REGEX;
import static com.mirishop.user.user.common.constants.UserConstants.USER_PASSWORD_LENGTH;

import com.mirishop.user.member.domain.Role;
import com.mirishop.user.member.entity.Member;
import com.mirishop.user.user.auth.domain.UserDetailsImpl;
import com.mirishop.user.member.repository.MemberRepository;
import com.mirishop.user.user.common.exception.ErrorCode;
import com.mirishop.user.user.common.exception.MemberException;
import com.mirishop.user.member.dto.ChangePasswordRequest;
import com.mirishop.user.member.dto.MemberJoinResponse;
import com.mirishop.user.member.dto.MemberRequest;
import com.mirishop.user.member.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    // 기본 이미지 경로는 추후 업로드 방식이 변경되면 수정 필요
    private static final String DEFAULT_PROFILE_IMAGE_PATH = "/uploads/images/default.jpg";
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * MemberRequest를 받아 회원을 등록하는 메소드
     * MemberRequest = email, password, nickname, profileImage, bio
     */
    @Override
    @Transactional
    public MemberJoinResponse register(final MemberRequest memberRequest) {
        String email = memberRequest.getEmail();
        String password = memberRequest.getPassword();
        String profileImagePath = memberRequest.getProfileImage();

        validateEmail(email);
        validatePassword(password);
        String profileImage = validateUploadProfileImage(profileImagePath);

        String encodedPassword = encodePassword(password);

        final Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(memberRequest.getNickname())
                .profileImage(profileImage)
                .bio(memberRequest.getBio())
                .role(Role.ROLE_USER) // role 설정
                .isDeleted(false) // 기본값 false
                .build();

        final Member savedMember = memberRepository.save(member);

        return new MemberJoinResponse(savedMember);
    }

    /**
     * 유저 정보와 수정 정보(MemberUpdateRequest)를 받아 업데이트하는 메소드
     * MemberUpdateRequest = nickname, profileImage, bio
     */
    @Override
    @Transactional
    public void update(MemberUpdateRequest memberUpdateRequest, UserDetailsImpl userDetails) {
        Member member = memberRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        String nickname = memberUpdateRequest.getNickname();
        String profileImagePath = memberUpdateRequest.getProfileImage();
        String bio = memberUpdateRequest.getBio();

        String profileImage = validateUploadProfileImage(profileImagePath);

        member.updateNickname(nickname);
        member.updateProfileImage(profileImage);
        member.updateBio(bio);

        memberRepository.save(member);
    }

    /**
     * 유저 정보와 비밀번호 정보(ChangePasswordRequest)를 받아 비밀번호를 업데이트하는 메소드
     * ChangePasswordRequest = oldPassword, newPassword
     * 토큰만으로 유저 검증은 가능하지만 본인확인 보안 차원으로 oldPasswor를 한번더 받는다.
     */
    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest, UserDetailsImpl userDetails) {
        Member member = memberRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        String storedPassword = member.getPassword();
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        validatePassword(newPassword);

        // 기존 비밀번호 검증 로직
        if (!isMatchesPassword(oldPassword, storedPassword)) {
            throw new MemberException(ErrorCode.WRONG_PASSWORD);
        }

        // 새로운 비밀번호가 기존 비밀번호와 같은 경우
        if (isMatchesPassword(newPassword, storedPassword)) {
            throw new MemberException(ErrorCode.INVALID_PASSWORD);
        }

        member.updatePassword(encodePassword(newPassword));
        memberRepository.save(member);
    }

    private void validateEmail(final String email) {
        validatedEmailForm(email);
        validatedDuplicatedEmail(email);
    }

    private void validatedEmailForm(final String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new MemberException(ErrorCode.INVALID_EMAIL_FROM);
        }
    }

    private void validatedDuplicatedEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(existingUser -> {
                    throw new MemberException(ErrorCode.DUPLICATED_EMAIL);
                });
    }

    private void validatePassword(String password) {
        if (password.length() < USER_PASSWORD_LENGTH) {
            throw new MemberException(ErrorCode.INVALID_PASSWORD_LENGTH);
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
