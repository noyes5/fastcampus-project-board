package com.mirishop.newsfeed.newsfeed.dto;

import com.mirishop.newsfeed.newsfeed.domain.NewsFeedType;
import com.mirishop.newsfeed.newsfeed.entity.NewsFeed;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsFeedResponse {

    private String id;
    private Long memberNumber;
    private NewsFeedType newsfeedType;
    private Long activityId;
    private Long targetPostId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;

    public static NewsFeedResponse fromNewsFeed(NewsFeed newsfeed) {
        return new NewsFeedResponse(
                newsfeed.getId(),
                newsfeed.getMemberNumber(),
                newsfeed.getNewsfeedType(),
                newsfeed.getActivityId(),
                newsfeed.getTargetPostId(),
                newsfeed.getCreatedAt(),
                newsfeed.getUpdatedAt(),
                newsfeed.getIsDeleted()
        );
    }
}
