package com.techeffic.blog.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于检查关键操作用户是否已是登录状态
 * @author xudong_liao<br/>
 * @date 2016年4月21日<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface IsLogined {

}
