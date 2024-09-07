package com.mirishop.newsfeed.newsfeed.domain;

import com.mirishop.newsfeed.common.exception.CustomException;
import com.mirishop.newsfeed.common.exception.ErrorCode;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
                .orElseThrow(() -> new CustomException(ErrorCode.NEWSFEED_TYPE_NOT_FOUND));
    }
}
