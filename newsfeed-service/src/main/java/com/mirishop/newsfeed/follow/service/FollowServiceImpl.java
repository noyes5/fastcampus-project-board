package com.mirishop.newsfeed.follow.service;

import com.mirishop.newsfeed.client.UserFeignClient;
import com.mirishop.newsfeed.common.exception.CustomException;
import com.mirishop.newsfeed.common.exception.ErrorCode;
import com.mirishop.newsfeed.follow.domain.FollowId;
import com.mirishop.newsfeed.follow.dto.FollowRequest;
import com.mirishop.newsfeed.follow.dto.FollowingIdsResponse;
import com.mirishop.newsfeed.follow.entity.Follow;
import com.mirishop.newsfeed.follow.repository.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowServiceImpl implements FollowService {

    private final UserFeignClient userFeignClient;
    private final FollowRepository followRepository;

    /**
     * 팔로우 유저 정보를 받아 팔로우를 만드는 메소드
     */
    @Override
    @Transactional
    public void follow(FollowRequest followRequest, Long currentMemberNumber) {
        Long followingMemberNumber = followRequest.getFollowingMemberNumber();
        // 자기 자신 팔로우인지 확인
        validateFollowSelf(followingMemberNumber, currentMemberNumber);

        // 멤버 DB 검증
        userFeignClient.findMemberByNumber(currentMemberNumber);
        userFeignClient.findMemberByNumber(followingMemberNumber);

        // 팔로우 가능한 멤버인지 검증
        FollowId followId = new FollowId(currentMemberNumber, followingMemberNumber);
        followRepository.findById(followId).ifPresent(f -> {
            throw new CustomException(ErrorCode.DUPLICATE_FOLLOW);
        });

        Follow follow = new Follow(followId);
        followRepository.save(follow);
    }

    /**
     * 팔로우 유저 정보를 받아 팔로우를 삭제하는 메소드
     */
    @Override
    @Transactional
    public void unfollow(FollowRequest followRequest, Long currentMemberNumber) {
        Long unfollowUserNumber = followRequest.getFollowingMemberNumber();
        FollowId followId = new FollowId(currentMemberNumber, unfollowUserNumber);

        Follow follow = followRepository.findById(followId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }

    /**
     * 유저가 팔로우한 모든 팔로잉들 조회하는 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public FollowingIdsResponse getFollowingIdsByMemberNumber(Long currentMemberNumber) {
        List<Long> followingIds =
                followRepository.findFollowingIdsByFollowerNumber(currentMemberNumber);

        return FollowingIdsResponse.builder().followIds(followingIds).build();
    }

    private void validateFollowSelf(Long currentMemberNumber, Long followMemberNumber) {
        if (currentMemberNumber.equals(followMemberNumber)) {
            throw new CustomException(ErrorCode.CANNOT_FOLLOW_SELF);
        }
    }
}
