package com.mirishop.activity.like.domain;

import com.mirishop.activity.common.exception.CustomException;
import com.mirishop.activity.common.exception.ErrorCode;
import java.util.stream.Stream;

public enum LikeType {

    POST(1), COMMENT(2);

    int value;

    LikeType(int value) {
        this.value = value;
    }

    public int toDbValue() {
        return value;
    }

    public static LikeType from(Integer dbData) {
        return Stream.of(LikeType.values())
                .filter(x -> x.value == dbData)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.CORRESPOND_LIKE_TYPE));
    }
}
