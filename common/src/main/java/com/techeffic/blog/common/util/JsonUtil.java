package com.techeffic.blog.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeffic.blog.common.exception.ExceptionCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * json操作工具类
 * Created by liaoxudong on 2017/5/5.
 */
public class JsonUtil {
    private static final Logger logger = LogManager.getLogger(JsonUtil.class);
    private static final ObjectMapper JACKSON = new ObjectMapper();

    /**
     * 将json字符串转换为指定对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json,Class<T> clazz){
        if(null == json) return null;
        try {
            return JACKSON.readValue(json,clazz);
        } catch (IOException e) {
            logger.error(ExceptionCode.SYS_ERROR.getCode(),e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转换为json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object){
        if(object != null){
            try {
                return JACKSON.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                logger.error(ExceptionCode.SYS_ERROR.getCode(),e);
                e.printStackTrace();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name","xiaoli");
        map.put("sex","xiaoli");
        map.put("age","xiaoli");
        System.out.println(fromJson(toJson(map),Map.class));
    }


}
