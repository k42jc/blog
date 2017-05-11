package com.techeffic.blog.common.cache.redis;

/**
 * redis处理对象型数据接口
 * Created by liaoxudong on 2017/5/9.
 */
public interface IRedisObjectService {

    /**
     * 存入永久对象
     * @param key
     * @param o
     */
    void putObject(String key,Object o);

    /**
     * 存入对象 设定过期时间
     * @param key
     * @param o
     * @param mills
     */
    void putObject(String key,Object o,long mills);

    /**
     * 获取对象
     * @param key
     * @return
     */
    Object getObject(String key);

    /**
     * 获取对象并转换为指定类型
     * @param key
     * @param clazz 类型
     * @param <T>
     * @return
     */
    <T> T getObject(String key,Class<T> clazz);


    /**
     * 删除指定对象
     * @param key
     */
    void deleteObject(String... key);


    /**
     * 获取以incrBy为增长因子的数字
     * @param key
     * @param incrBy
     * @return
     */
    long incrBy(String key,long incrBy);
}
