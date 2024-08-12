package com.hh.mirishop.newsfeed.newsfeed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsFeedDelete {

    private Long activityId;
    private String newsFeedType;  // 활동 유형 : post/comment/like
    private Boolean isDeleted;
}
