package com.mirishop.userservice.api.controller;

import com.mirishop.userservice.application.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    /**
     * 로그아웃 시 redis에 저장된 refreshToken을 삭제합니다.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("X-USER-ID") String userId) {
        logoutService.logout(userId);

        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }
}
