package com.techeffic.blog.schedule;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techeffic.blog.service.ISitePushService;

/**
 * 被定时器反射调用的POJO
 * @author k42jc
 *
 */
@Component
public class ScheduleExecutor {
	@Autowired
	private ISitePushService sitePushService;
	
	public void executeByJdk() {
		execute("jdk timer job");
	}

	public void executeBySpringCronByJava() {
		execute("spring cron job by java");
	}

	// 被Spring的Quartz MethodInvokingJobDetailFactoryBean反射执行
	public void executeByQuartzLocalJob() {
		execute("quartz local job");
	}

	// 被Spring的Scheduler namespace 反射构造成ScheduledMethodRunnable
	public void executeBySpringCronByXml() {
		execute("spring cron job by xml");
	}

	// 被Spring的Scheduler namespace 反射构造成ScheduledMethodRunnable
	public void executeBySpringTimerByXml() {
		execute("spring timer job by xml");
	}
	
	private void execute(String by){
		Logger logger = Logger.getLogger(ScheduleExecutor.class.getName()+"-"+by);
		Map<String,Object> result = sitePushService.scheduleSitePush();
		if(result.get("success") !=null && !result.get("success").equals("")){
            logger.info(result.get("success")+"个链接已成功提交");
        }else{
        	logger.error("链接提交未成功！");
        }
	}
}
