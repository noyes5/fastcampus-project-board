package com.hh.mirishop.newsfeed.newsfeed.controller;

import com.hh.mirishop.newsfeed.common.dto.BaseResponse;
import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedResponse;
import com.hh.mirishop.newsfeed.newsfeed.service.NewsFeedQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/newsfeeds")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedQueryService newsFeedQueryService;

    /**
     * 나의 뉴스피드 정보를 불러올 수 있습니다.
     * Page객체로 불러옵니다.
     */
    @GetMapping("/my")
    public ResponseEntity<BaseResponse<Page<NewsFeedResponse>>> getMyNewsFeed(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber) {
        Page<NewsFeedResponse> newsFeed = newsFeedQueryService.getNewsfeedForMember(page - 1, size, currentMemberNumber);

        return ResponseEntity.ok(BaseResponse.of("뉴스피드 조회 성공", true, newsFeed));
    }
}
