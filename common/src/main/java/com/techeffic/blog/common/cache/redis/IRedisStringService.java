package com.techeffic.blog.common.cache.redis;

/**
 * redis 字符串操作
 * Created by liaoxudong on 2017/5/9.
 */
public interface IRedisStringService {

    /**
     * 存储字符串
     * @param key
     * @param value
     */
    void putString(String key, String value);

    /**
     * 存储字符串 设置过期时间
     * @param key
     * @param value
     * @param mills
     */
    void putString(String key, String value, long mills);

    /**
     * 获取字符串 不存在返回null
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 获取字符串并设置默认值
     * @param key
     * @param defaultValue
     * @return
     */
    String getString(String key, String defaultValue);

    /**
     * key存在不做任何操作 不存在设置值
     * @param key
     * @param value
     */
    void setNX(String key, String value);

    /**
     * key存在不做任何操作 不存在设置值且设置过期时间
     * @param key
     * @param value
     * @param mills
     */
    void setNX(String key,String value,long mills);
}
