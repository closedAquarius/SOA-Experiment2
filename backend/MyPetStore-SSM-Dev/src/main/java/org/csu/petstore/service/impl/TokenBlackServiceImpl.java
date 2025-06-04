package org.csu.petstore.service.impl;

import org.csu.petstore.service.TokenBlackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class TokenBlackServiceImpl implements TokenBlackService {
    private StringRedisTemplate redisTemplate;
    private static final String BLACKLIST_PREFIX = "bl_";

    public TokenBlackServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Value("${jwt.token-validity:86400}")
    private long defaultTokenValidity;

    @Override
    public boolean addToBlackList(String token) {
        if(token == null || token.isEmpty()) return false;
        String key = getTokenKey(token);
        long ttl = defaultTokenValidity;
        redisTemplate.opsForValue().set(
                key,"1",ttl, TimeUnit.SECONDS
        );
        return true;
    }

    @Override
    public boolean isInBlackList(String token) {
        if(token == null || token.isEmpty()) return false;
        String key = getTokenKey(token);
        return Boolean.TRUE.equals(
                redisTemplate.hasKey(key)
        );
    }

    private String getTokenKey(String token) {
        return BLACKLIST_PREFIX + DigestUtils.md5DigestAsHex(token.getBytes());
    }
}
