package com.techeffic.blog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用于标记xml标签内需要CDATA[]包裹的内容
 * @author xudong_liao<br/>
 * @date 2016年1月5日<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface XStreamCDATA {

}
