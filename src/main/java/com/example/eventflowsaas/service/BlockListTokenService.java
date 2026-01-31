package com.example.eventflowsaas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BlockListTokenService {
    private static final String REDIS_PREFIX = "jwt:blacklist:";
    private final StringRedisTemplate stringRedisTemplate;

    public BlockListTokenService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void addTokenToBlockList(String token) {
        stringRedisTemplate.opsForValue().set(
                 REDIS_PREFIX + token, "true", 24, TimeUnit.HOURS
        );
        log.info("Token added to Redis blacklist!");    }

    public boolean isTokenInBlockList(String token) {
        return token != null && Boolean.TRUE.equals(stringRedisTemplate.hasKey(REDIS_PREFIX + token));
    }
}