package com.techeffic.blog.tags;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.ServiceFactory;
/**
 * 基础tag 用于获取封装request/reponse
 * @author k42jc
 *
 */
public class BaseTags{
	protected volatile static WebContext webCtx;
	protected static ServiceFactory serviceFactory;
	
	static{
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		webCtx =WebContext.init(attributes.getRequest(),attributes.getResponse());
		serviceFactory = SpringContextHolder.getBean(ServiceFactory.class);
	}
}
