package com.techeffic.blog.dal.datasource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;

/**
 * 数据源服务接口
 * Created by liaoxudong on 2017/5/16.
 */
public interface IDataSourceService {

    /**
     * 获取默认数据库分区的唯一标识
     * @return
     */
    String getDefaultIdentity();

    /**
     * 获取数据源集合
     * @return
     */
    Map<String,DataSource> getDataSources();

    /**
     * 获取分区数据源集合
     * @return
     */
    Set<DataSourceDescriptor> getDataSourceDescriptors();
}
