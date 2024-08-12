package com.hh.mirishop.newsfeed.common.exception;

import lombok.Getter;

@Getter
public class FollowException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public FollowException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}