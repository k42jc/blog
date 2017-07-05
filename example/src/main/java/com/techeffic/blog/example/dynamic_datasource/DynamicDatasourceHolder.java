package com.techeffic.blog.example.dynamic_datasource;

/**
 * 动态数据源提供类
 * 使用ThreadLocl绑定当前线程使用的数据源key
 * Created by liaoxudong on 2017/7/4.
 */
public class DynamicDatasourceHolder {

    private static final String MASTER = "master";

    private static final String SLAVE = "slave";

    // 用于记录当前线程的数据源key
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void puDatasourceKey(String key) {
        holder.set(key);
    }

    public static String getDatasourceKey(){
        return holder.get();
    }

    public static void markMaster(){
        puDatasourceKey(MASTER);
    }

    public static void markSlave(){
        puDatasourceKey(SLAVE);
    }
}
