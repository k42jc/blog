package com.techeffic.blog.common.cache.redis;

import org.springframework.data.redis.connection.MessageListener;

/**
 * redis列表类型操作接口
 * pop操作使用阻塞类型实现
 * Created by liaoxudong on 2017/5/9.
 */
public interface IRedisListService {
    int BRPOP_DEFAULT_TIMEOUT = 60;

    /**
     * 向左端放入元素
     * @param key
     * @param value
     */
    void lpush(String key,long mills,Object... value);

    /**
     * 往右端放入元素
     * @param key
     * @param value
     */
    void rpush(String key,long mills, Object... value);

    /**
     * 从右端弹出第一个元素 获取&删除
     * @param key
     * @param seconds 阻塞超时时间
     * @return
     */
    <T> T rpop(String key,Class<T> clazz,int seconds);

    /**
     * 从左端弹出第一个元素 获取&删除
     * @param key
     * @return
     */
    <T> T lpop(String key,Class<T> clazz,int seconds);

    /**
     * 消息发布
     */
    void publish(String key,Object value,long mills);

    /**
     * 消息订阅
     */
    void subscribe(String key, MessageListener listener);



}
