package com.techeffic.blog.common.cache.redis;

import com.techeffic.blog.common.constants.Constants;
import com.techeffic.blog.common.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

/**
 * 对象类型操作
 * Created by liaoxudong on 2017/5/9.
 */
@Service
public class RedisObjectService extends RedisService implements IRedisObjectService{

    private static final Logger logger = LogManager.getLogger(RedisObjectService.class);
    @Override
    public void putObject(String key, Object o) {
        if(StringUtil.isEmpty(key)){
            return;
        }
        Object o1 = redisTemplate.execute((RedisCallback) (connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            connection.hSet(serializer.serialize(key), serializer.serialize("o"), redisTemplate.getDefaultSerializer().serialize(o));
            return null;
        });
        logger.info("redis put Object key：【{}】，value：【{}】",key,o);
    }

    @Override
    public void putObject(String key, Object o, long mills) {
        if(StringUtil.isEmpty(key)){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[] k =serializer.serialize(key);
            connection.hSet(k, serializer.serialize("o"), redisTemplate.getDefaultSerializer().serialize(o));
            connection.expire(k,mills>0?mills:Constants.REDIS_DEFAULT_EXPIRE);
            return null;
        });
        logger.info("redis put Object key：【{}】，value：【{}】，expire time：【{}】",key,o,mills);
    }

    @Override
    public Object getObject(String key) {
        if(StringUtil.isEmpty(key)){
            return null;
        }
        return redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[] k =serializer.serialize(key);
            if(connection.exists(k)){
                return redisTemplate.getDefaultSerializer().deserialize(connection.hGet(k,serializer.serialize("o")));
            }
            return null;
        });
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        return (T)getObject(key);
    }

    @Override
    public void deleteObject(String... keys) {
        if(keys == null || keys.length <= 0){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[][] ks = new byte[keys.length][];
            for(int i=0;i<keys.length;i++){
                ks[i] = serializer.serialize(keys[i]);
            }
            connection.del(ks);
            return null;
        });
    }

    @Override
    public long incrBy(String key, long incrBy) {
        if(StringUtil.isEmpty(key)){
            return 0;
        }
        return (long) redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            return connection.incrBy(serializer.serialize(key),incrBy);
        });
    }
}
