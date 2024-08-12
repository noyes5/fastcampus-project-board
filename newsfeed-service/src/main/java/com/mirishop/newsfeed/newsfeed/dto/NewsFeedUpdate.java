package com.hh.mirishop.newsfeed.newsfeed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NewsFeedUpdate {

    private Long activityId;
    private String newsFeedType;  // 활동 유형 : post/comment/like
    private LocalDateTime updatedAt;
}
