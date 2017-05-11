package com.techeffic.blog.common.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 缓存操作工厂
 * 非String类型 redis操作key统一使用stringSerializeble，value使用defaultSerializeble
 * String类型 key/value操作序列化都使用stringSerializble
 * Created by liaoxudong on 2017/5/9.
 */
@Component
public class CacheFactory {
    private CacheFactory(){}

    @Autowired
    private static IRedisObjectService redisObjectService;
    @Autowired
    private static IRedisListService redisListService;
    @Autowired
    private static IRedisStringService redisStringService;

    /**
     * 存入永久对象
     * @param key
     * @param o
     */
    public static void putObject(String key,Object o){
        redisObjectService.putObject(key, o);
    }

    /**
     * 存入对象 设定过期时间
     * @param key
     * @param o
     * @param mills
     */
    public static void putObject(String key,Object o,long mills){
        redisObjectService.putObject(key, o, mills);
    }

    /**
     * 获取对象
     * @param key
     * @return
     */
    public static Object getObject(String key){
        return redisObjectService.getObject(key);
    }

    /**
     * 获取对象并转换为指定类型
     * @param key
     * @param clazz 类型
     * @param <T>
     * @return
     */
    public static <T> T getObject(String key,Class<T> clazz){
        return redisObjectService.getObject(key, clazz);
    }


    /**
     * 删除指定对象/字符串
     * @param key
     */
    public static void delete(String... key){
        redisObjectService.deleteObject(key);
    }


    /**
     * 获取以incrBy为增长因子的数字
     * @param key
     * @param incrBy
     * @return
     */
    public static long incrBy(String key,long incrBy){
        return redisObjectService.incrBy(key, incrBy);
    }

    /**
     * 向左端放入元素
     * @param key
     * @param value
     */
    public static void lpush(String key,long mills,Object... value){
        redisListService.lpush(key, mills, value);
    }

    /**
     * 往右端放入元素
     * @param key
     * @param value
     */
    public static void rpush(String key,long mills, Object... value){
        redisListService.rpush(key, mills, value);
    }

    /**
     * 从右端弹出第一个元素 获取&删除
     * @param key
     * @param seconds 阻塞超时时间
     * @return
     */
    public static <T> T rpop(String key,Class<T> clazz,int seconds){
        return redisListService.rpop(key, clazz, seconds);
    }

    /**
     * 从左端弹出第一个元素 获取&删除
     * @param key
     * @return
     */
    public static <T> T lpop(String key,Class<T> clazz,int seconds){
        return redisListService.lpop(key, clazz, seconds);
    }

    /**
     * 消息发布
     */
    public static void publish(String key,Object value,long mills){
        redisListService.publish(key, value, mills);
    }

    /**
     * 消息订阅
     */
    public static void subscribe(String key, MessageListener listener){
        redisListService.subscribe(key, listener);
    }

    /**
     * 存储字符串
     * @param key
     * @param value
     */
    public static void putString(String key, String value){
        redisStringService.putString(key, value);
    }

    /**
     * 存储字符串 设置过期时间
     * @param key
     * @param value
     * @param mills
     */
    public static void putString(String key, String value, long mills){
        redisStringService.putString(key, value, mills);
    }

    /**
     * 获取字符串 不存在返回null
     * @param key
     * @return
     */
    public static String getString(String key){
        return redisStringService.getString(key);
    }

    /**
     * 获取字符串并设置默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String key, String defaultValue){
        return redisStringService.getString(key, defaultValue);
    }

    /**
     * key存在不做任何操作 不存在设置值
     * @param key
     * @param value
     */
    public static void setNX(String key, String value){
        redisStringService.setNX(key, value);
    }

    /**
     * key存在不做任何操作 不存在设置值且设置过期时间
     * @param key
     * @param value
     * @param mills
     */
    public static void setNX(String key,String value,long mills){
        redisStringService.setNX(key, value, mills);
    }


}
