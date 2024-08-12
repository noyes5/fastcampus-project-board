package com.mirishop.user.user.auth.controller;

import com.mirishop.user.user.auth.dto.LoginRequest;
import com.mirishop.user.user.auth.dto.TokenRequest;
import com.mirishop.user.user.auth.dto.TokenResponse;
import com.mirishop.user.user.auth.service.LoginService;
import com.mirishop.user.user.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인 성공시 token을 발급하여 보여줍니다.
     */
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse token = loginService.login(loginRequest);
        return ResponseEntity.ok().body(BaseResponse.of("로그인 완료", true, token));
    }

    /**
     * 토큰 재발급 - redis에서 refresh token을 검증하여, 두 토큰 다 재발급합니다.
     */
    @PostMapping("/refreshToken")
    public ResponseEntity<BaseResponse<TokenResponse>> reissueToken(@RequestBody TokenRequest tokenRequest) {
        TokenResponse token = loginService.reissue(tokenRequest.getRefreshToken());
        return ResponseEntity.ok().body(BaseResponse.of("토큰 재발급 완료", true, token));
    }
}
