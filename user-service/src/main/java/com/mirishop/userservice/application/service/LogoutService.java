package com.mirishop.userservice.application.service;

import com.mirishop.userservice.common.exception.CustomException;
import com.mirishop.userservice.common.exception.ErrorCode;
import com.mirishop.userservice.infrastructure.redis.RedisService;
import com.mirishop.userservice.domain.user.UserEntity;
import com.mirishop.userservice.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogoutService {

    private final UserRepository userRepository;
    private final RedisService redisService;

    /**
     * 로그아웃 메소드
     */
    public void logout(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        redisService.deleteData(userEntity.getEmail());
    }
}
