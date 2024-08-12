package com.hh.mirishop.newsfeed.common.exception;

import lombok.Getter;

@Getter
public class NewsFeedException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public NewsFeedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
