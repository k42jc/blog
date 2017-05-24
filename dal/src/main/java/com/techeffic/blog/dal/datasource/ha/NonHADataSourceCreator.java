package com.techeffic.blog.dal.datasource.ha;

import com.techeffic.blog.dal.datasource.DataSourceDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;

/**
 * 非高可用式数据源提供实现
 * 只获取独立的单个数据源
 * Created by liaoxudong on 2017/5/16.
 */
public class NonHADataSourceCreator implements IHADataSourceCreator {
    private static final Logger logger = LogManager.getLogger(NonHADataSourceCreator.class);

    public NonHADataSourceCreator() {
        logger.info("create a NonHA instance，Just return activeDataSource only...");
    }

    @Override
    public DataSource createHADataSource(DataSourceDescriptor descriptor) throws Exception {
        return descriptor.getactiveDataSource();
    }
}
