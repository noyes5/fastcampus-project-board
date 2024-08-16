package com.mirishop.userservice.auth.service;

import com.mirishop.userservice.common.exception.CustomException;
import com.mirishop.userservice.common.exception.ErrorCode;
import com.mirishop.userservice.common.redis.service.RedisService;
import com.mirishop.userservice.user.entity.User;
import com.mirishop.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final UserRepository userRepository;
    private final RedisService redisService;

    /**
     * 로그아웃 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public void logout(Long memberNumber) {
        User user = userRepository.findByNumberAndIsDeletedFalse(memberNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        redisService.deleteData(user.getEmail());
    }
}
