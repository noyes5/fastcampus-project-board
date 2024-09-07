package com.mirishop.orderpayment.common.exception;

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

    // 서버 에러 관련
    NOT_FOUND(HttpStatus.BAD_REQUEST, "요청사항을 찾을 수 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
