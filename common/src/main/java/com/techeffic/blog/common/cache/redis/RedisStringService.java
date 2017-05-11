package com.techeffic.blog.common.cache.redis;

import com.techeffic.blog.common.constants.Constants;
import com.techeffic.blog.common.util.StringUtil;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

/**
 * redis字符串操作
 * Created by liaoxudong on 2017/5/9.
 */
@Service
public class RedisStringService extends RedisService implements IRedisStringService{

    @Override
    public void putString(String key, String value) {
        if(StringUtil.isEmpty(key)){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            connection.set(redisTemplate.getStringSerializer().serialize(key), redisTemplate.getDefaultSerializer().serialize(value));
            return null;
        });
    }

    @Override
    public void putString(String key, String value, long mills) {
        if(StringUtil.isEmpty(key)){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
            byte[] k = stringSerializer.serialize(key);
            connection.set(k, stringSerializer.serialize(value));
            connection.expire(k,mills>0?mills: Constants.REDIS_DEFAULT_EXPIRE);
            return null;
        });
    }

    @Override
    public String getString(String key) {
        if(StringUtil.isEmpty(key)){
            return null;
        }
        return (String) redisTemplate.execute((RedisCallback)(connection) -> {
            byte[] k = redisTemplate.getStringSerializer().serialize(key);
            if(!connection.exists(k)) return null;
            byte[] value = connection.get(k);
            return redisTemplate.getStringSerializer().deserialize(value);
        });
    }

    @Override
    public String getString(String key, String defaultValue) {
        if(StringUtil.isEmpty(key)){
            return defaultValue;
        }
        return (String) redisTemplate.execute((RedisCallback)(connection) -> {
            byte[] k = redisTemplate.getStringSerializer().serialize(key);
            if(!connection.exists(k)) return defaultValue;
            byte[] value = connection.get(k);
            return redisTemplate.getStringSerializer().deserialize(value);
        });
    }

    @Override
    public void setNX(String key, String value) {
        if(StringUtil.isEmpty(key)){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
            byte[] k = stringSerializer.serialize(key);
            connection.setNX(k, stringSerializer.serialize(value));
            return null;
        });
    }

    @Override
    public void setNX(String key, String value, long mills) {
        if(StringUtil.isEmpty(key)){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
            byte[] k = stringSerializer.serialize(key);
            connection.setNX(k, stringSerializer.serialize(value));
            connection.expire(k,mills>0?mills: Constants.REDIS_DEFAULT_EXPIRE);
            return null;
        });
    }
}
