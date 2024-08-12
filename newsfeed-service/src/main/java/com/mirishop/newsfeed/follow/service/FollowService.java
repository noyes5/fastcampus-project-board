package com.hh.mirishop.newsfeed.follow.service;

import com.hh.mirishop.newsfeed.follow.dto.FollowRequest;
import com.hh.mirishop.newsfeed.follow.dto.FollowingIdsResponse;


public interface FollowService {

    void follow(FollowRequest followRequest, Long currentMemberNumber);

    void unfollow(FollowRequest followRequest, Long currentMemberNumber);

    FollowingIdsResponse getFollowingIdsByMemberNumber(Long currentMemberNumber);
}
