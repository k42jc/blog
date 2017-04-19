package com.techeffic.blog.web.boot;

import com.techeffic.blog.common.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 服务启动入口
 * @author liaoxudong
 *
 */
@Configuration  
@ComponentScan 
@EnableAutoConfiguration
public class ApplicationBoot extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{
	private static final Logger logger = LoggerFactory.getLogger(ApplicationBoot.class);
//	private static final int PORT = ConfigUtils.CONFIG.getProperty("server.boot.port", Integer.class);
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(ApplicationBoot.class);
	}
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
//		logger.info("端口.......【"+PORT+"】");
		int PORT =ConfigUtils.PROP.getProperty("server.boot.port", Integer.class);
		container.setPort(PORT);
	}


	public static void main(String[] args) {
		int PORT =ConfigUtils.PROP.getProperty("server.boot.port", Integer.class);
		logger.info("服务启动开始...");
		ConfigUtils.PROP.getProperty("server.boot.port", Integer.class);
//		SpringApplication.run(ApplicationBoot.class, args);
		ConfigUtils.PROP.getProperty("server.boot.port", Integer.class);
		logger.info("服务启动完成...");

		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("测试开辟线程配置文件加载会不会多次");
				ConfigUtils.PROP.getProperty("server.boot.port");
			}
		},"new Thread").start();
	}

}
