package com.techeffic.blog.dal.datasource.ha;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 被动数据源切换实现
 * 使用AOP实现 判断获取连接过程是否正常 否则切换数据源
 * Created by liaoxudong on 2017/5/16.
 */
public class PassiveFailoverDataSource implements MethodInterceptor,InitializingBean{
    private static final Logger logger = LogManager.getLogger(PassiveFailoverDataSource.class);


    private static final int DEFAULT_RETRY_TIMES = 3;

    private SQLStateSQLExceptionTranslator sqlExTranslator = new SQLStateSQLExceptionTranslator();

//    private Integer swapTimesThreshold = Integer.MAX_VALUE;

//    private Integer retryTimes = DEFAULT_RETRY_TIMES;

//    private long retryInterval = 1000;

//    private String detectingSql = "SELECT 1";

    private HotSwappableTargetSource targetSource;
    private DataSource mainDataSource;
    private DataSource standbyDataSource;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (!StringUtils.equalsIgnoreCase(methodInvocation.getMethod().getName(), "getConnection")) {
            return methodInvocation.proceed();
        }

        try {
            return methodInvocation.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof SQLException) {
                DataAccessException dae = sqlExTranslator.translate("将异常翻译为是否资源故障导致的异常",null,(SQLException) throwable);
                if(dae instanceof DataAccessResourceFailureException){
                    logger.warn("从当前数据源中获取连接异常：",throwable);
                    swapDataSource();
                    return methodInvocation.getMethod().invoke(targetSource.getTarget(), methodInvocation.getArguments());

                }
            }
            throw throwable;
        }
    }

    /**
     * 数据源切换操作
     */
    private void swapDataSource() {

        synchronized (targetSource) {
            DataSource target = (DataSource) getTargetSource().getTarget();
            if(target == mainDataSource){
                logger.warn("hot swap from 【{}】 to 【{}】",target,standbyDataSource);
                getTargetSource().swap(standbyDataSource);
            }else{
                logger.warn("hot swap from 【{}】 to 【{}】",target,mainDataSource);
                getTargetSource().swap(mainDataSource);

            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public static int getDefaultRetryTimes() {
        return DEFAULT_RETRY_TIMES;
    }

    public SQLStateSQLExceptionTranslator getSqlExTranslator() {
        return sqlExTranslator;
    }

    public void setSqlExTranslator(SQLStateSQLExceptionTranslator sqlExTranslator) {
        this.sqlExTranslator = sqlExTranslator;
    }

    /*public Integer getSwapTimesThreshold() {
        return swapTimesThreshold;
    }

    public void setSwapTimesThreshold(Integer swapTimesThreshold) {
        this.swapTimesThreshold = swapTimesThreshold;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }*/

    /*public String getDetectingSql() {
        return detectingSql;
    }

    public void setDetectingSql(String detectingSql) {
        this.detectingSql = detectingSql;
    }*/

    public HotSwappableTargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(HotSwappableTargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public DataSource getMainDataSource() {
        return mainDataSource;
    }

    public void setMainDataSource(DataSource mainDataSource) {
        this.mainDataSource = mainDataSource;
    }

    public DataSource getStandbyDataSource() {
        return standbyDataSource;
    }

    public void setStandbyDataSource(DataSource standbyDataSource) {
        this.standbyDataSource = standbyDataSource;
    }
}
