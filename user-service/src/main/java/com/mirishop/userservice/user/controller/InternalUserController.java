package com.mirishop.userservice.user.controller;

import com.mirishop.userservice.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/members")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserQueryService userQueryService;

    /**
     * 다른 서비스로부터 memberNumber를 받아 유저가 존재하는지 확인합니다.
     */
    @GetMapping("/{memberNumber}")
    public boolean existsMemberByNumber(@PathVariable("memberNumber") Long memberNumber) {
        return userQueryService.existsMemberByNumber(memberNumber);
    }
}
