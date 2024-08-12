package com.hh.mirishop.productmanagement.common.lock.annotation;

import com.hh.mirishop.productmanagement.config.LockConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redisson으로 분산락을 제공하는 어노테이션
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    LockConfig lockConfig();
}
