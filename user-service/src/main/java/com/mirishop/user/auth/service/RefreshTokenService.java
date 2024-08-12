package com.mirishop.user.auth.service;

import com.mirishop.user.auth.infrastructure.JwtTokenProvider;
import com.mirishop.user.common.redis.service.RedisService;
import com.mirishop.user.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisService redisService;

    @Transactional
    public String createRefreshToken(HttpServletRequest request) {
        // todo: request에서 refreshtoken 확인 후 생성하는 메소드
        return null;
    }
}
