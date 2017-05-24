package com.techeffic.blog.dal.datasource.ha;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.target.HotSwappableTargetSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.*;

/**
 * 实现积极/主动切换
 * 以预设值轮询连接是否可用
 * 提供监控数据源一旦发现当前数据源不可用即切换
 * Created by liaoxudong on 2017/5/16.
 */
public class PositiveFailoverDataSource implements Runnable{

    private static final Logger logger = LogManager.getLogger(PositiveFailoverDataSource.class);

    // 监控检测SQL语句
    private String detectingSQL;
    // 超时时间
    private long detectingRequestTimeout;
    // 重新检查间隔时间
    private long recheckInterval;
    // 重新检查次数
    private int recheckTimes;

    private HotSwappableTargetSource hotSwappableTargetSource;
    private DataSource masterDataSource;
    private DataSource standbyDataSource;
    private DataSource masterDetectorDataSource;
    private DataSource standbyDetectorDatasource;

    private DataSource currentDetectorDataSource;

    // 异步检测任务 若执行时间超过设定了超时阙值，在切换到standbyDatasource之前会检查一次
    private ExecutorService executorService;

    public PositiveFailoverDataSource(ExecutorService service) {
        Validate.notNull(service);
        this.executorService = service;
    }

    @Override
    public void run() {// 执行监控&数据源切换操作
        Future<Integer> future = executorService.submit((Callable) () -> {
            Integer result = -1;// 指定标志位 对监控数据源(与操作数据源为同一个)尝试连接如果成功则置0
            for (int i = 0; i < getRecheckTimes(); i++) {
                Connection conn = null;
                /**
                 *   通过对指定数据源进行特定的连接操作 如果正常连接则表示正常
                 *   否则切换数据源
                 *
                 */
                try {
                    conn = getCurrentDetectorDataSource().getConnection();
                    PreparedStatement preparedStatement = conn.prepareStatement(getDetectingSQL());
                    preparedStatement.execute();
                    preparedStatement.close();
                    result = 0;
                    break;
                } catch (Exception e) {
                    logger.warn("第【{}】次连接检查失败，睡眠【{}】毫秒后进入下一次检查...",i+1,getRecheckInterval(),e);
                    TimeUnit.MILLISECONDS.sleep(getRecheckInterval());
                    continue;
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            logger.warn("监控连接关闭失败：", e);
                        }
                    }
                }
            }
            return result;
        });

        try {
            Integer result = future.get(getDetectingRequestTimeout(), TimeUnit.MILLISECONDS);
            //在超时阙值后依旧连接失败 则切换数据源
            if(result == -1){
                logger.warn("检测到当前数监控据源无法连接，将执行数据源切换操作...");
                swapDataSource();
            }
        } catch (Exception e) {
            logger.error("超过超时检测阙值，执行数据源切换操作...",e);
            swapDataSource();
        }
    }


    /**
     * 数据源切换具体操作
     */
    private void swapDataSource() {
        synchronized (hotSwappableTargetSource) {
            DataSource target = (DataSource)getHotSwappableTargetSource().getTarget();
            if (target == masterDataSource) {
                logger.warn("swap datasource from 【{}】 to 【{}】",target,standbyDataSource);
                getHotSwappableTargetSource().swap(standbyDataSource);
                logger.warn("swap detectorDatasource from 【{}】 to 【{}】",currentDetectorDataSource,standbyDataSource);
                currentDetectorDataSource = standbyDetectorDatasource;
            }else{
                logger.warn("swap datasource from 【{}】 to 【{}】",target,masterDataSource);
                getHotSwappableTargetSource().swap(masterDataSource);
                logger.warn("swap detectorDatasource from 【{}】 to 【{}】",currentDetectorDataSource,masterDetectorDataSource);
                currentDetectorDataSource = masterDetectorDataSource;
            }
        }
    }

    public String getDetectingSQL() {
        return detectingSQL;
    }

    public void setDetectingSQL(String detectingSQL) {
        this.detectingSQL = detectingSQL;
    }

    public long getDetectingRequestTimeout() {
        return detectingRequestTimeout;
    }

    public void setDetectingRequestTimeout(long detectingRequestTimeout) {
        this.detectingRequestTimeout = detectingRequestTimeout;
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

    public HotSwappableTargetSource getHotSwappableTargetSource() {
        return hotSwappableTargetSource;
    }

    public void setHotSwappableTargetSource(HotSwappableTargetSource hotSwappableTargetSource) {
        this.hotSwappableTargetSource = hotSwappableTargetSource;
    }

    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public DataSource getStandbyDataSource() {
        return standbyDataSource;
    }

    public void setStandbyDataSource(DataSource standbyDataSource) {
        this.standbyDataSource = standbyDataSource;
    }

    public DataSource getMasterDetectorDataSource() {
        return masterDetectorDataSource;
    }

    public void setMasterDetectorDataSource(DataSource masterDetectorDataSource) {
        this.masterDetectorDataSource = masterDetectorDataSource;
    }

    public DataSource getStandbyDetectorDatasource() {
        return standbyDetectorDatasource;
    }

    public void setStandbyDetectorDatasource(DataSource standbyDetectorDatasource) {
        this.standbyDetectorDatasource = standbyDetectorDatasource;
    }

    public DataSource getCurrentDetectorDataSource() {
        return currentDetectorDataSource;
    }

    public void setCurrentDetectorDataSource(DataSource currentDetectorDataSource) {
        this.currentDetectorDataSource = currentDetectorDataSource;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
