package com.techeffic.blog.common.cache.redis;

import com.techeffic.blog.common.constants.Constants;
import com.techeffic.blog.common.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liaoxudong on 2017/5/9.
 */
@Service
public class RedisListService extends RedisService implements IRedisListService{

    private static final Logger logger = LogManager.getLogger(RedisListService.class);
    @Override
    public void lpush(String key,long mills, Object... values) {
        if(StringUtil.isEmpty(key) || values.length <= 0){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[] k = serializer.serialize(key);
            byte[][] vals = new byte[values.length][];
            for(int i=0;i<values.length;i++){
                vals[i]=redisTemplate.getDefaultSerializer().serialize(values[i]);
            }
            connection.lPush(k, vals);
            connection.expire(k,mills>0?mills: Constants.REDIS_DEFAULT_EXPIRE);
            return null;
        });
        logger.info("push to list【{}】 left，values：【{}】",key,values);
    }

    @Override
    public void rpush(String key,long mills, Object... values) {
        if(StringUtil.isEmpty(key) || values.length <= 0){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[] k = serializer.serialize(key);
            byte[][] vals = new byte[values.length][];
            for(int i=0;i<values.length;i++){
                vals[i]=redisTemplate.getDefaultSerializer().serialize(values[i]);
            }
            connection.rPush(k, vals);
            connection.expire(k,mills>0?mills: Constants.REDIS_DEFAULT_EXPIRE);
            return null;
        });
        logger.info("push to list【{}】 right，values：【{}】",key,values);
    }

    @Override
    public <T> T rpop(String key,Class<T> clazz,int seconds) {
        if(StringUtil.isEmpty(key)){
            return null;
        }
        return (T) redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[] k = serializer.serialize(key);
            if(!connection.exists(k)) return null;
            List<byte[]> bytes = connection.bRPop(seconds>0?seconds:BRPOP_DEFAULT_TIMEOUT, k);
            if(bytes.size() <= 0) return null;
            return (T)redisTemplate.getDefaultSerializer().deserialize(bytes.get(1));
        });
    }

    @Override
    public <T> T lpop(String key,Class<T> clazz,int seconds) {
        if(StringUtil.isEmpty(key)){
            return null;
        }
        return (T) redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[] k = serializer.serialize(key);
            if(!connection.exists(k)) return null;
            List<byte[]> bytes = connection.bLPop(seconds>0?seconds:BRPOP_DEFAULT_TIMEOUT, k);
            if(bytes.size() <= 0) return null;
            return (T)redisTemplate.getDefaultSerializer().deserialize(bytes.get(1));
        });
    }

    @Override
    public void publish(String key,Object message,long mills) {
        if(StringUtil.isEmpty(key) || message == null){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[] k = serializer.serialize(key);
            connection.publish(k, redisTemplate.getDefaultSerializer().serialize(message));
            connection.expire(k,mills>0?mills: Constants.REDIS_DEFAULT_EXPIRE);
            return null;
        });
    }

    @Override
    public void subscribe(String key, MessageListener listener) {
        if(StringUtil.isEmpty(key)){
            return;
        }
        redisTemplate.execute((RedisCallback)(connection) -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            byte[] k = serializer.serialize(key);
            connection.subscribe(listener,k);
            return null;
        });
    }
}
