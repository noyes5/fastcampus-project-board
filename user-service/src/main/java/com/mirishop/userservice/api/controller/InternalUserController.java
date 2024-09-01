package com.mirishop.userservice.api.controller;

import com.mirishop.userservice.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserQueryService userQueryService;

    /**
     * 유저 유무 확인 메소드
     */
    @GetMapping
    public boolean existsMemberByNumber(@RequestParam("userId") String userId) {
        return userQueryService.getExistsUser(userId);
    }
}
