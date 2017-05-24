package com.techeffic.blog.dal.datasource;

import javax.sql.DataSource;

/**
 * 分区目标数据源
 *          Client
 *         /     \
 *       /        \
 *    ActiveDB <-> StandbyDB
 *
 * Created by liaoxudong on 2017/5/16.
 */
public class DataSourceDescriptor {

    // 数据库分区的唯一标识
    private String identity;

    // 当前使用数据源
    private DataSource activeDataSource;

    // HA(高可用数据库集群)探测用数据源，用于检查<{@link #activeDataSource}> 的状态，
    // 通常指向与 activeDataSource 相同的数据库，为防止干扰，数据库连接池需要单独配置
    private DataSource activeDetectorDataSource;

    // 备用数据源，当配置了HA功能，当 activeDataSource不可用时，自动切换
    private DataSource standbyDataSource;

    // 备用数据源对应的HA监控数据源
    private DataSource standbyDetectorDataSource;

    // 每个数据源的初始化连接池大小 默认指定为 CPU数量*5
    private int poolSize = Runtime.getRuntime().availableProcessors() * 5;

    // 是否指定默认数据库，当找不到分区时提供默认选择数据源
    private boolean defaultDataSource = false;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public DataSource getactiveDataSource() {
        return activeDataSource;
    }

    public void setactiveDataSource(DataSource activeDataSource) {
        this.activeDataSource = activeDataSource;
    }

    public DataSource getactiveDetectorDataSource() {
        return activeDetectorDataSource;
    }

    public void setactiveDetectorDataSource(DataSource activeDetectorDataSource) {
        this.activeDetectorDataSource = activeDetectorDataSource;
    }

    public DataSource getStandbyDataSource() {
        return standbyDataSource;
    }

    public void setStandbyDataSource(DataSource standbyDataSource) {
        this.standbyDataSource = standbyDataSource;
    }

    public DataSource getStandbyDetectorDataSource() {
        return standbyDetectorDataSource;
    }

    public void setStandbyDetectorDataSource(DataSource standbyDetectorDataSource) {
        this.standbyDetectorDataSource = standbyDetectorDataSource;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public boolean isDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(boolean defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    // 重写hashcode equals toString方法


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identity == null) ? 0 : identity.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DataSourceDescriptor descriptor = (DataSourceDescriptor) obj;
        if (identity == null) {
            if (descriptor.identity != null) {
                return false;
            }
        } else if (!identity.equals(descriptor.identity)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataSourceDescriptor [identity = " + identity + ", poolSize = " + poolSize + "，standbyDataSource = " + standbyDataSource + "，standbyDetectorDataSource = " + standbyDetectorDataSource + "，activeDataSource=" + activeDataSource + "，activeDetectorDataSource=" + activeDetectorDataSource + "]";
    }
}
