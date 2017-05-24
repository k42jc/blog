package com.techeffic.blog.dal.datasource.ha;

import com.techeffic.blog.dal.datasource.DataSourceDescriptor;

import javax.sql.DataSource;

/**
 * 高可用集群式数据源获取接口
 * Created by liaoxudong on 2017/5/16.
 */
public interface IHADataSourceCreator {

    DataSource createHADataSource(DataSourceDescriptor descriptor) throws Exception;
}
