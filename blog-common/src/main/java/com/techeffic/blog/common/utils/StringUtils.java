package com.techeffic.blog.common.utils;

/**
 * 字符类操作工具类
 * Created by liaoxudong on 2017/4/18.
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param val 待判断字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String val){
        return (null == val || val.trim().length() == 0)?true:false;
    }

    /**
     * 字符串转换为指定对象类型
     * @param str 字符串
     * @param clazz 指定类型
     * @param <T> 泛型
     * @return 转换后的值
     */
    public static <T> T strToObj(String str,Class<T> clazz){
        if (clazz.equals(Byte.class)) {
            return (T) Byte.valueOf(str.trim());
        } else if (clazz.equals(Short.class)) {
            return (T) Short.valueOf(str.trim());
        } else if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(str.trim());
        } else if (clazz.equals(Long.class)) {
            return (T) Long.valueOf(str.trim());
        } else if (clazz.equals(Float.class)) {
            return (T) Float.valueOf(str.trim());
        } else if (clazz.equals(Double.class)) {
            return (T) Double.valueOf(str.trim());
        } else if (clazz.equals(Character.class)) {
            return (T) Character.valueOf(str.trim().toCharArray()[0]);
        } else if (clazz.equals(String.class)) {
            return (T) str.trim();
        }
        return null;
    }
}
