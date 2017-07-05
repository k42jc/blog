package com.techeffic.blog.example.dynamic_datasource;

import org.aopalliance.intercept.Joinpoint;

/**
 * 定义数据源AOP切面，通过Service的方法名判断应该走读库还是写库
 * Created by liaoxudong on 2017/7/4.
 */
public class DataSourceAspect {

    public void before(Joinpoint joinpoint) {
//        String methodName = joinpoint.getStaticPart().get
    }
}
