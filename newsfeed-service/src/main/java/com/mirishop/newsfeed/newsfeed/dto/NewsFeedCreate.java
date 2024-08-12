package com.hh.mirishop.newsfeed.newsfeed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NewsFeedCreate {

    private String id;
    private Long memberNumber;
    private String newsFeedType; // String으로 POST/COMMENT/LIKE 넘어오므로 변환필요
    private Long activityId; // 업데이트 활동 ID (게시글 ID, 댓글 ID, 좋아요 ID)
    private Long targetPostId; // 해당하는 활동이 속한 게시글 ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
