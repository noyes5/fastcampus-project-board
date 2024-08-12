package com.mirishop.user.common.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * AuthRedis : 유저의 refreshToken을 저장하는 redis
 */
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String getValue(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setValues(String key, String value, Long expiration, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expiration, timeUnit);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public boolean doesKeyExist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean checkExistValue(String value) {
        return !value.equals("false");
    }
}
