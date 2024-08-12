package com.hh.mirishop.newsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // follow 에러 관련
    CANNOT_FOLLOW_SELF(HttpStatus.BAD_REQUEST,"자기 자신은 팔로우 할 수 없습니다."),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우 유저를 찾을 수 없습니다."),
    DUPLICATE_FOLLOW(HttpStatus.CONFLICT,"이미 팔로우한 유저는 팔로우 할 수 없습니다."),

    // newsfeed 에러 관련
    NEWSFEED_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 활동의 타입을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
