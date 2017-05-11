package com.techeffic.blog.common.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis基础操作
 * Created by liaoxudong on 2017/5/9.
 */
public abstract class RedisService {
//    private static final Logger logger = LogManager.getLogger(RedisService.class);

    static RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisService.redisTemplate = redisTemplate;
//        logger.info("redisTemplate setter success...");
    }
    /*
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }*/
}
