package com.techeffic.blog.dal.datasource;

import com.techeffic.blog.dal.datasource.ha.IDataSourcePostProcessor;
import com.techeffic.blog.dal.datasource.ha.IHADataSourceCreator;
import com.techeffic.blog.dal.datasource.ha.NonHADataSourceCreator;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * 默认数据源服务
 * Created by liaoxudong on 2017/5/16.
 */
public class DefaultDataSourceService extends AbstractDataSource implements IDataSourceService,InitializingBean{
    private static final Logger logger = LogManager.getLogger(DefaultDataSourceService.class);

    //默认数据源标识
    private String defaultIdentity;
    //数据源集合
    private Map<String, DataSource> dataSources = new HashMap<>();

    // 高可用集群数据源
    private IHADataSourceCreator haDataSourceCreator;
    //分区数据源集合
    private Set<DataSourceDescriptor> dataSourceDescriptors = new HashSet<>();

    private List<IDataSourcePostProcessor> dataSourcePostProcessors = new ArrayList<>();


    public DefaultDataSourceService() {
        logger.info("create defaultDataSourceService instance");
    }

    @Override
    public String getDefaultIdentity() {
        return this.defaultIdentity;
    }

    @Override
    public Map<String, DataSource> getDataSources() {
        return this.dataSources;
    }

    @Override
    public Set<DataSourceDescriptor> getDataSourceDescriptors() {
        return this.dataSourceDescriptors;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (getHaDataSourceCreator() == null) {
            // 未预定义HADataSource则直接指定NonHADataSourceCreator
            setHaDataSourceCreator(new NonHADataSourceCreator());
        }
        if(CollectionUtils.isEmpty(dataSourceDescriptors)){
            throw new IllegalArgumentException("【dataSourceDescriptors】需指定至少一个可用dataSourceDescriptor");
        }
        for (DataSourceDescriptor descriptor : dataSourceDescriptors) {
            Validate.notEmpty(descriptor.getIdentity());
            Validate.notNull(descriptor.getactiveDataSource());

            DataSource activeDataSource = descriptor.getactiveDataSource();
            if (descriptor.getStandbyDataSource() != null) {
                activeDataSource = getHaDataSourceCreator().createHADataSource(descriptor);
                if(!CollectionUtils.isEmpty(dataSourcePostProcessors)){
                    for (IDataSourcePostProcessor pp : dataSourcePostProcessors) {
                        activeDataSource = pp.postProcess(activeDataSource);
                    }
                }
            }
            logger.info("创建LazyConnectionDataSourceProxy...");
            dataSources.put(descriptor.getIdentity(), new LazyConnectionDataSourceProxy(activeDataSource));
            if (descriptor.isDefaultDataSource()) {
                defaultIdentity = descriptor.getIdentity();
            }

        }


    }

    public void setDefaultIdentity(String defaultIdentity) {
        this.defaultIdentity = defaultIdentity;
    }

    public void setDataSources(Map<String, DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public IHADataSourceCreator getHaDataSourceCreator() {
        return haDataSourceCreator;
    }

    public void setHaDataSourceCreator(IHADataSourceCreator haDataSourceCreator) {
        this.haDataSourceCreator = haDataSourceCreator;
    }

    public void setDataSourceDescriptors(Set<DataSourceDescriptor> dataSourceDescriptors) {
        this.dataSourceDescriptors = dataSourceDescriptors;
    }

    public List<IDataSourcePostProcessor> getDataSourcePostProcessors() {
        return dataSourcePostProcessors;
    }

    public void setDataSourcePostProcessors(List<IDataSourcePostProcessor> dataSourcePostProcessors) {
        this.dataSourcePostProcessors = dataSourcePostProcessors;
    }
}
