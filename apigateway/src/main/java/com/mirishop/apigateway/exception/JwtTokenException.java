package com.mirishop.apigateway.exception;


import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public JwtTokenException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
