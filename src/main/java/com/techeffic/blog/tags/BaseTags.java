package com.techeffic.blog.tags;

import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.service.ServiceFactory;
/**
 * 基础tag 用于获取封装request/reponse
 * @author k42jc
 *
 */
public class BaseTags{
	protected static ServiceFactory serviceFactory;
	
	static{
		serviceFactory = SpringContextHolder.getBean(ServiceFactory.class);
	}
}
