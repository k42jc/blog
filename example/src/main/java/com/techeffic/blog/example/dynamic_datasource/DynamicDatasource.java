package com.techeffic.blog.example.dynamic_datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源，实现spring提供的AbstractRoutingDatasource，只需要实现determineCurrentLookupKey即可
 * SpringBean是单例、线程不安全的，所以采用ThreadLocal保证线程安全，使用DynamicDataSourceHolder完成
 * Created by liaoxudong on 2017/7/4.
 */
public class DynamicDatasource extends AbstractRoutingDataSource{

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDatasourceHolder.getDatasourceKey();
    }
}
