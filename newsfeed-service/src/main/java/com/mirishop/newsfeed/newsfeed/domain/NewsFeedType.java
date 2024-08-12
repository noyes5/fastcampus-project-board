package com.hh.mirishop.newsfeed.newsfeed.domain;

import com.hh.mirishop.newsfeed.common.exception.ErrorCode;
import com.hh.mirishop.newsfeed.common.exception.NewsFeedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum NewsFeedType {
    POST,
    COMMENT,
    LIKE,
    ;

    public static NewsFeedType of(String newsFeedTypeStr) {
        return Arrays.stream(NewsFeedType.values())
                .filter(type -> type.name().equalsIgnoreCase(newsFeedTypeStr))
                .findFirst()
                .orElseThrow(() -> new NewsFeedException(ErrorCode.NEWSFEED_TYPE_NOT_FOUND));
    }
}
