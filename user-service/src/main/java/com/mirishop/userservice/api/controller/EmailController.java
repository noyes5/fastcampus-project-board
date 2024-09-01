package com.mirishop.userservice.api.controller;

import com.mirishop.userservice.common.dto.BaseResponse;
import com.mirishop.userservice.api.dto.request.EmailRequest;
import com.mirishop.userservice.application.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    /**
     * 이메일 정보를 받아 인증번호를 요청합니다.
     */
    @PostMapping("/email-authentication")
    public ResponseEntity<BaseResponse<Void>> verifyEmail(@Valid @RequestBody EmailRequest request) {
        emailService.createRandomCode(request);
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

}
