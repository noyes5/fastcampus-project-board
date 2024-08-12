package com.mirishop.user.user.auth.service;

import com.mirishop.user.user.common.exception.ErrorCode;
import com.mirishop.user.user.common.exception.MemberException;
import com.mirishop.user.user.common.redis.service.AuthRedisService;
import com.mirishop.user.member.entity.Member;
import com.mirishop.user.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final MemberRepository memberRepository;
    private final AuthRedisService authRedisService;

    /**
     * 로그아웃 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public void logout(Long memberNumber) {
        Member member = memberRepository.findByNumberAndIsDeletedFalse(memberNumber)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        authRedisService.deleteData(member.getEmail());
    }
}
