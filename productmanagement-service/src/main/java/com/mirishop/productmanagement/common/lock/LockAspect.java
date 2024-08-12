package com.hh.mirishop.productmanagement.common.lock;

import com.hh.mirishop.productmanagement.common.lock.annotation.DistributedLock;
import com.hh.mirishop.productmanagement.config.LockConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class LockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(distributedLock)")
    public Object executeFunctionWithLock(ProceedingJoinPoint proceedingJoinPoint, DistributedLock distributedLock)
            throws Throwable {
        LockConfig lockConfig = distributedLock.lockConfig();
        String lockName = lockConfig.name();
        String methodName = proceedingJoinPoint.getSignature().getName();

        log.info("{} 에서 lock: {} 을 획득하려 시도", methodName, lockName);
        var lock = redissonClient.getLock(lockName);

        boolean tryLock = lock.tryLock(lockConfig.getWaitTimeOutMills(), lockConfig.getLeaseTimeoutMills(),
                TimeUnit.MILLISECONDS);

        if (!tryLock) {
            log.error("LOCK을 획득할 수 없습니다.");
            throw new IllegalStateException("Unable to acquire lock");
        }

        try {
            log.info("{} 에서 lock: {} 을 획득하려 시도", methodName, lockName);
            return proceedingJoinPoint.proceed();
        } finally {
            lock.unlock();
            log.info("{} 에서 lock: {} 을 해제", methodName, lockName);
        }
    }
}