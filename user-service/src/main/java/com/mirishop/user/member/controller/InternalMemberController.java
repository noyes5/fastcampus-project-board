package com.mirishop.user.member.controller;

import com.mirishop.user.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/members")
@RequiredArgsConstructor
public class InternalMemberController {

    private final MemberQueryService memberQueryService;

    /**
     * 다른 서비스로부터 memberNumber를 받아 유저가 존재하는지 확인합니다.
     */
    @GetMapping("/{memberNumber}")
    public boolean existsMemberByNumber(@PathVariable("memberNumber") Long memberNumber) {
        return memberQueryService.existsMemberByNumber(memberNumber);
    }
}
