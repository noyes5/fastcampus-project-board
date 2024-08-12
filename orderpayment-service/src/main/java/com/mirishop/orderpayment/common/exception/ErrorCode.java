package com.hh.mirishop.orderpayment.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // order 에러 관련
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문이 존재하지 않습니다."),
    ORDER_STATUS_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 주문입니다."),
    INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, "주문 상태가 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
