package com.techeffic.blog.schedule;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.techeffic.blog.util.ThreadUtil;

/**
 * 使用Spring的ThreadPoolTaskScheduler执行Cron式任务的类.
 * 相比Spring的Task NameSpace配置方式, 不需要反射調用，并强化了退出超时控制.
 * @author k42jc
 *
 */
public class SpringCronJob implements Runnable{
	//定时控制 cron表达式 由配置文件指定
	private String cronExpression;
	//超时退出时间 单位秒 默认为最大 由配置文件指定覆盖
	private int shutdownTimeout = Integer.MAX_VALUE;
	
	private ThreadPoolTaskScheduler taskSchedule;
	@Autowired
	private ScheduleExecutor scheduleExecutor;
	
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	public void setShutdownTimeout(int shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

	@Override
	public void run() {
		scheduleExecutor.executeBySpringCronByJava();
	}
	@PostConstruct
	public void start(){
		Validate.notBlank(cronExpression);
		
		taskSchedule = new ThreadPoolTaskScheduler();
		taskSchedule.setThreadNamePrefix("SpringCromJob");
		taskSchedule.initialize();
		
		taskSchedule.schedule(this, new CronTrigger(cronExpression));
	}
	
	/**
	 * 停止
	 */
	@PreDestroy
	public void stop(){
		ScheduledExecutorService executorService = taskSchedule.getScheduledExecutor();
		ThreadUtil.normalShutdown(executorService, shutdownTimeout, TimeUnit.SECONDS);
	}

}
