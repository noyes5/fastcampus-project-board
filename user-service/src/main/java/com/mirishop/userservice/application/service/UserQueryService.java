package com.mirishop.userservice.application.service;

import com.mirishop.userservice.api.dto.response.UserDetailResponse;
import com.mirishop.userservice.common.exception.CustomException;
import com.mirishop.userservice.common.exception.ErrorCode;
import com.mirishop.userservice.domain.user.UserEntity;
import com.mirishop.userservice.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;

    /**
     * 유저 정보를 받아 존재하는지 boolean을 리턴하는 내부용 메소드
     */
    public boolean getExistsUser(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    /**
     * memberNumber를 받아 회원 상세 정보 조회하는 메소드
     */
    public UserDetailResponse getUserDetail(String userId) {
        return userRepository.findByUserId(userId)
                .map(UserDetailResponse::new)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 전체 회원 목록을 조회하는 메소드
     */
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }
}
