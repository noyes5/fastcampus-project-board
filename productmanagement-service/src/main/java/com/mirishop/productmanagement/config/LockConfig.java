package com.hh.mirishop.productmanagement.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LockConfig {

    TEST_LOCK(1000L, 3000L); // 예시 lock 설정

    private final long waitTimeOutMills;
    private final long leaseTimeoutMills;
}
