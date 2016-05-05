package com.techeffic.blog.listener.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.techeffic.blog.service.ServiceFactory;

/**
 * 基础spring监听器
 * @author k42jc
 *
 * @param <E>
 */
public abstract class BaseListener<E extends ApplicationEvent> implements ApplicationListener<E>{
	
	protected boolean isRootLoad;
	
	protected boolean isWebLoad;

	@Override
	public void onApplicationEvent(E event) {
		//ContextRefreshedEvent spring容器启动完成后的刷新事件 可以用于容器启动完成之后执行某个事件
		isRootLoad = false;
		isWebLoad = false;
		if(event instanceof ContextRefreshedEvent ){
			loadCompleteExecute((ContextRefreshedEvent) event);
		}
	}
	
	protected void loadCompleteExecute(ContextRefreshedEvent event){
		//spring容器
		if(event.getApplicationContext().getDisplayName().startsWith("Root")){
			isRootLoad = true;
		}
		//springMVC
		if(event.getApplicationContext().getDisplayName().startsWith("WebApplicationContext")){
			isWebLoad = true;
		}
	}
	
}
