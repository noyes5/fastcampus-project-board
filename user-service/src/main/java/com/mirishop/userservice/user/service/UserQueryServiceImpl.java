package com.mirishop.userservice.user.service;

import com.mirishop.userservice.common.exception.CustomException;
import com.mirishop.userservice.common.exception.ErrorCode;
import com.mirishop.userservice.user.dto.UserDetailResponse;
import com.mirishop.userservice.user.dto.UserListResponse;
import com.mirishop.userservice.user.entity.User;
import com.mirishop.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    /**
     * 유저 정보를 받아 존재하는지 boolean을 리턴하는 내부용 메소드
     */
    @Override
    @Cacheable(value = "userCache", key = "#root.args[0]")
    @Transactional(readOnly = true)
    public boolean existsMemberByNumber(Long memberNumber) {
        return userRepository.findByNumberAndIsDeletedFalse(memberNumber).isPresent();
    }

    /**
     * memberNumber를 받아 회원 상세 정보 조회하는 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetailResponse getMemberDetail(Long memberNumber) {
        return userRepository.findByNumberAndIsDeletedFalse(memberNumber)
                .map(UserDetailResponse::new)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 전체 회원 목록을 조회하는 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserListResponse> listMembers() {
        List<User> users = userRepository.findAllByIsDeletedFalse();
        return users.stream()
                .map(UserListResponse::new) // Member 엔티티를 리스트 DTO로 변환
                .toList();
    }
}
