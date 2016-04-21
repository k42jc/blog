package com.techeffic.blog.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.ServiceFactory;

/**
 * 基础拦截器 封装webContext对象及service工厂
 * @author xudong_liao<br/>
 * @date 2016年4月21日<br/>
 */
public abstract class BaseInterceptor extends HandlerInterceptorAdapter{
	
	private ServiceFactory serviceFactory;
	
	@Autowired
	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	public ServiceFactory getServiceFactory() {
		return serviceFactory;
	}


	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return this.preHandle(WebContext.init(request, response), handler);
	}
	
	public abstract boolean preHandle(WebContext webCtx,Object handler);
}
