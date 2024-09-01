package com.mirishop.userservice.api.controller;

import com.mirishop.userservice.api.dto.request.LoginRequest;
import com.mirishop.userservice.api.dto.request.TokenRequest;
import com.mirishop.userservice.api.dto.response.TokenResponse;
import com.mirishop.userservice.application.service.AuthService;
import com.mirishop.userservice.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 성공시 token을 발급하여 보여줍니다.
     */
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse token = authService.login(loginRequest);
        return ResponseEntity.ok().body(BaseResponse.of("로그인 완료", true, token));
    }

    /**
     * 토큰 재발급 - redis에서 refresh token을 검증하여, 두 토큰 다 재발급합니다.
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<TokenResponse>> reissueToken(@RequestBody TokenRequest tokenRequest) {
        TokenResponse token = authService.reissue(tokenRequest.getRefreshToken());
        return ResponseEntity.ok().body(BaseResponse.of("토큰 재발급 완료", true, token));
    }
}
