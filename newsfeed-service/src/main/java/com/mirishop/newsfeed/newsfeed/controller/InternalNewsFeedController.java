package com.hh.mirishop.newsfeed.newsfeed.controller;

import com.hh.mirishop.newsfeed.common.dto.BaseResponse;
import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedCreate;
import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedDelete;
import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedUpdate;
import com.hh.mirishop.newsfeed.newsfeed.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/newsfeeds")
@RequiredArgsConstructor
public class InternalNewsFeedController {

    private final NewsFeedService newsFeedService;

    /**
     * NewsFeed를 생성할 수 있는 정보를 받아 newsFeed를 생성합니다.
     */
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createNewsFeed(@RequestBody NewsFeedCreate newsFeedCreate) {
        newsFeedService.createNewsFeed(newsFeedCreate);
        return ResponseEntity.ok(BaseResponse.of("뉴스피드 생성됨", true, null));
    }

    /**
     * NewsFeed를 수정 할 수 있는 정보를 받아 newsFeed를 수정합니다.
     */
    @PutMapping
    public ResponseEntity<BaseResponse<Void>> updateNewsFeed(@RequestBody NewsFeedUpdate newsFeedUpdate) {
        newsFeedService.updateNewsFeed(newsFeedUpdate);

        return ResponseEntity.ok(BaseResponse.of("뉴스피드 수정됨", true, null));
    }

    /**
     * NewsFeed를 삭제 할 수 있는 정보를 받아 newsFeed를 수정합니다.
     * NewsFeedDelete에는 활동Id, 뉴스피드 타입, 삭제 여부를 담습니다.
     */
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteNewsFeed(@RequestBody NewsFeedDelete newsFeedDelete) {
        newsFeedService.deleteNewsFeed(newsFeedDelete);

        return  ResponseEntity.ok(BaseResponse.of("뉴스피드 삭제됨", true, null));
    }
}
