package com.hh.mirishop.newsfeed.follow.controller;

import com.hh.mirishop.newsfeed.common.dto.BaseResponse;
import com.hh.mirishop.newsfeed.follow.dto.FollowRequest;
import com.hh.mirishop.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {

    private final FollowService followService;

    /**
     * FollowRequest로 팔로우 유저 아이디를 받아 팔로우를 생성합니다.
     */
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> follow(@RequestBody final FollowRequest followRequest,
                                                     @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber) {
        followService.follow(followRequest, currentMemberNumber);

        return ResponseEntity.ok(BaseResponse.of("팔로우가 완료되었습니다.", true, null));
    }

    /**
     * FollowRequest로 팔로우 유저 아이디를 받아 팔로우를 삭제합니다.
     */
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> unfollow(@RequestBody final FollowRequest followRequest,
                                                       @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber) {
        followService.unfollow(followRequest, currentMemberNumber);

        return ResponseEntity.ok(BaseResponse.of("언팔로우가 완료되었습니다.", true, null));
    }
}
