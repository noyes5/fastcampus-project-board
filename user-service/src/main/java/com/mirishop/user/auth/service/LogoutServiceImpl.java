package com.mirishop.user.auth.service;

import com.mirishop.user.common.exception.ErrorCode;
import com.mirishop.user.common.exception.MemberException;
import com.mirishop.user.common.redis.service.RedisService;
import com.mirishop.user.member.entity.Member;
import com.mirishop.user.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final MemberRepository memberRepository;
    private final RedisService redisService;

    /**
     * 로그아웃 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public void logout(Long memberNumber) {
        Member member = memberRepository.findByNumberAndIsDeletedFalse(memberNumber)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        redisService.deleteData(member.getEmail());
    }
}
