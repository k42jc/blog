package com.techeffic.blog.dal.datasource.ha;

import com.techeffic.blog.common.util.StringUtil;
import com.techeffic.blog.dal.datasource.DataSourceDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * active数据源故障情况下可自动切换数据源
 * 使用org.springframework.aop.target.HotSwappableTargetSource 热交换数据源实现
 *
 * Created by liaoxudong on 2017/5/16.
 */
public class FailoverDataSourceCreator implements IHADataSourceCreator ,InitializingBean , DisposableBean{

    private static final Logger logger = LogManager.getLogger(FailoverDataSourceCreator.class);

    // 被动故障转移
    private boolean passiveFailoverEnable = false;

    // 主动故障转移
    private boolean positiveFailoverEnable = true;

    // 注册用于异步检查数据库状态的定时任务
    private ConcurrentMap<ScheduledFuture<?>, ScheduledExecutorService> scheduledFutures = new ConcurrentHashMap<>();

    private List<ExecutorService> jobExecutorRegistry = new ArrayList<>();

    // 线程执行周期
    private long monitorPeriod = 15000;

    // 初次执行延迟
    private int initialDelay = 0;

    // 用于检查连接的sql
    private String detectingSql;

    // 检测超时时间
    private long detectingTimeoutThreshold = 15000;

    // 下次检测等待时间
    private long recheckInterval = 5000;
    // 检查次数
    private int recheckTimes = 3;
    @Override
    public DataSource createHADataSource(DataSourceDescriptor descriptor) throws Exception {
        DataSource activeDataSource = descriptor.getactiveDataSource();
        DataSource standbyDataSource = descriptor.getStandbyDataSource();
        //至少提供一个可用数据源
        if (activeDataSource == null && standbyDataSource == null) {
            throw new IllegalArgumentException("Must provide at least one active datasource");
        }
        // 如果只有单个可用数据源 则直接返回 表示只能提供NonHADataSource支持
        if(activeDataSource == null || standbyDataSource == null){
            logger.warn("Only one datasource provided,So no HA support");
            return activeDataSource == null?standbyDataSource:activeDataSource;
        }

        HotSwappableTargetSource hotSwappableSource = new HotSwappableTargetSource(activeDataSource);
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[]{DataSource.class});
        proxyFactory.setTargetSource(hotSwappableSource);

        //积极切换数据源方案
        if(isPositiveFailoverEnable()){
            DataSource activeDetectorDataSource = descriptor.getactiveDetectorDataSource();
            DataSource standbyDetectorDataSource = descriptor.getStandbyDetectorDataSource();
            if (activeDetectorDataSource == null || standbyDetectorDataSource == null) {
                throw new IllegalArgumentException("activeDetectorDataSource or standbyDetectorDataSource can`t be null when positive failover is enabled!");
            }
            // 创建故障切换监视器
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
            ExecutorService jobExecutor = Executors.newFixedThreadPool(1);
            jobExecutorRegistry.add(jobExecutor);
            PositiveFailoverDataSource job = new PositiveFailoverDataSource(jobExecutor);
            // 为监控程序注入相关依赖
            job.setHotSwappableTargetSource(hotSwappableSource);
            job.setCurrentDetectorDataSource(activeDetectorDataSource);
            job.setMasterDataSource(activeDataSource);
            job.setMasterDetectorDataSource(activeDetectorDataSource);
            job.setStandbyDataSource(standbyDataSource);
            job.setStandbyDetectorDatasource(standbyDetectorDataSource);
            job.setDetectingRequestTimeout(getDetectingTimeoutThreshold());
            job.setDetectingSQL(getDetectingSql());
            job.setRecheckInterval(getRecheckInterval());
            job.setRecheckTimes(getRecheckTimes());
            // start scheduling and keep reference for canceling and shutdown
            ScheduledFuture<?> scheduledFuture = scheduledThreadPool.scheduleWithFixedDelay(job, initialDelay, monitorPeriod, TimeUnit.MILLISECONDS);
            scheduledFutures.put(scheduledFuture, scheduledThreadPool);
        }
        // 消极切换 以Aop方式实现 截取获取连接的过程 异常则切换数据源
        if (isPassiveFailoverEnable()) {
            PassiveFailoverDataSource passiveFailoverDataSource = new PassiveFailoverDataSource();
            passiveFailoverDataSource.setTargetSource(hotSwappableSource);
            passiveFailoverDataSource.setMainDataSource(activeDataSource);
            passiveFailoverDataSource.setStandbyDataSource(standbyDataSource);
            proxyFactory.addAdvice(passiveFailoverDataSource);
        }
        return (DataSource) proxyFactory.getProxy();
    }

    @Override
    public void destroy() throws Exception {
        for(Map.Entry<ScheduledFuture<?>,ScheduledExecutorService> entry:scheduledFutures.entrySet()){
            ScheduledFuture<?> future = entry.getKey();
            ScheduledExecutorService scheduledExecutorService = entry.getValue();

            future.cancel(true);
            shutdownExecutor(scheduledExecutorService);
        }

        for(ExecutorService executorService:jobExecutorRegistry){
            shutdownExecutor(executorService);
        }

    }

    /**
     * 关闭ExecutorService操作
     * @param executorService
     */
    private void shutdownExecutor(ExecutorService executorService) throws InterruptedException{
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(!isPassiveFailoverEnable() && !isPositiveFailoverEnable()){
            logger.warn("【passive/positive】 must injection at least one!");
            return;
        }
        if(isPositiveFailoverEnable() && StringUtil.isEmpty(getDetectingSql())){
            throw new IllegalArgumentException("A 【detectingSql】 must be provided when positive is enable");
        }

        if (monitorPeriod <= 0 || detectingTimeoutThreshold <= 0 || recheckInterval <= 0 || recheckTimes <= 0) {
            throw new IllegalArgumentException("【monitorPeriod】【detectingTimeoutThreshold】【recheckInterval】【recheckTimes】 必须指定为大于0");
        }
        if(isPositiveFailoverEnable()){
            if(detectingTimeoutThreshold > monitorPeriod){// 指定线程池的执行周期必须大于具体任务的执行周期
                throw new IllegalArgumentException("【monitorPeriod】必须大于或等于【detectingTimeoutThreshold】");
            }
            if(recheckInterval * recheckTimes > detectingTimeoutThreshold){// 超时时间必须大于具体任务的总执行时间
                throw new IllegalArgumentException("【detectingTimeoutThreshold】必须大于等于具体任务的总执行时间：【recheckInterval * recheckTimes】");
            }
        }

    }

    public boolean isPassiveFailoverEnable() {
        return passiveFailoverEnable;
    }

    public void setPassiveFailoverEnable(boolean passiveFailoverEnable) {
        this.passiveFailoverEnable = passiveFailoverEnable;
    }

    public boolean isPositiveFailoverEnable() {
        return positiveFailoverEnable;
    }

    public void setPositiveFailoverEnable(boolean positiveFailoverEnable) {
        this.positiveFailoverEnable = positiveFailoverEnable;
    }

    public String getDetectingSql() {
        return detectingSql;
    }

    public void setDetectingSql(String detectingSql) {
        this.detectingSql = detectingSql;
    }

    public long getDetectingTimeoutThreshold() {
        return detectingTimeoutThreshold;
    }

    public void setDetectingTimeoutThreshold(long detectingTimeoutThreshold) {
        this.detectingTimeoutThreshold = detectingTimeoutThreshold;
    }

    public long getRecheckInterval() {
        return recheckInterval;
    }

    public void setRecheckInterval(long recheckInterval) {
        this.recheckInterval = recheckInterval;
    }

    public int getRecheckTimes() {
        return recheckTimes;
    }

    public void setRecheckTimes(int recheckTimes) {
        this.recheckTimes = recheckTimes;
    }
}
