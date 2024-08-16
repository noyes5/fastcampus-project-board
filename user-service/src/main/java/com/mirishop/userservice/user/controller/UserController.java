package com.mirishop.userservice.user.controller;

import com.mirishop.userservice.auth.domain.UserDetailsImpl;
import com.mirishop.userservice.common.dto.BaseResponse;
import com.mirishop.userservice.email.dto.EmailRequest;
import com.mirishop.userservice.email.service.EmailService;
import com.mirishop.userservice.user.dto.*;
import com.mirishop.userservice.user.service.ImageUploadService;
import com.mirishop.userservice.user.service.UserQueryService;
import com.mirishop.userservice.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserQueryService userQueryService;
    private final EmailService emailService;
    private final ImageUploadService imageUploadService;

    /**
     * 회원가입 정보를 받아 Member를 생성 후, MemberJoinResponse를 리턴합니다.
     * MemberJoinResponse : memberNumber, email 포함.
     */
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserJoinResponse>> register(
            @RequestBody @Valid final UserRequest userRequest) {
        UserJoinResponse joinMember = userService.register(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of("회원가입 성공", true, joinMember));
    }

    /**
     * 이메일 정보를 받아 인증번호를 요청합니다.
     */
    @PostMapping("/email-authentication")
    public ResponseEntity<BaseResponse<Void>> requestEmailVerification(@RequestBody @Valid EmailRequest request) {
        emailService.authEmail(request);
        return ResponseEntity.ok(BaseResponse.of("이메일 인증 요청 성공", true, null));
    }

    /**
     * 이메일 정보와 인증코드를 받아 검증합니다.
     */
    @GetMapping("/email-verification")
    public ResponseEntity<BaseResponse<String>> verifyEmail(@RequestParam("email") String email,
                                                            @RequestParam("verificationCode") String verificationCode) {
        boolean isVerified = emailService.verityEmail(email, verificationCode);
        if (isVerified) {
            return ResponseEntity.ok(BaseResponse.of("이메일 인증 성공", true, "이메일 인증 성공"));
        }
        return ResponseEntity.badRequest().body(BaseResponse.of("이메일 인증 실패", false, "이메일 인증 실패"));
    }

    /**
     * 파일을 받아 저장된 이미지 경로를 반환합니다.
     */
    @PostMapping("/upload/image")
    public ResponseEntity<BaseResponse<String>> uploadProfileImage(@RequestParam("image") MultipartFile imageFile)
            throws IOException {
            log.info("이미지 생성 메소드 진입");
            String imagePath = imageUploadService.uploadImage(imageFile);
            return ResponseEntity.ok(BaseResponse.of("이미지 업로드 성공", true, imagePath)); // 저장된 이미지의 경로 반환
    }

    /**
     * 유저 아이디를 받아 정보를 조회합니다.
     */
    @GetMapping("/{memberNumber}")
    public ResponseEntity<BaseResponse<UserDetailResponse>> getMemberDetail(
            @PathVariable("memberNumber") Long memberNumber) {
        UserDetailResponse memberDetail = userQueryService.getMemberDetail(memberNumber);
        return ResponseEntity.ok(BaseResponse.of("유저 정보를 불러왔습니다.", true, memberDetail));
    }

    /**
     * 유저 정보를 받아 유저정보를 수정합니다.
     */
    @PatchMapping
    public ResponseEntity<BaseResponse<Void>> update(
            @Valid @RequestBody UserUpdateRequest userUpdateRequest, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        userService.update(userUpdateRequest, userDetails);
        return ResponseEntity.ok().body(BaseResponse.of("유저 정보 변경 완료", true, null));
    }

    /**
     * 유저 정보를 받아 비밀번호를 수정합니다.
     */
    @PutMapping("/password")
    public ResponseEntity<BaseResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        userService.changePassword(changePasswordRequest, userDetails);
        return ResponseEntity.ok().body(BaseResponse.of("패스워드 변경 완료", true, null));
    }
}
