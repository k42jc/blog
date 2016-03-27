package com.techeffic.blog.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jetbrick.template.JetTemplate;
import jetbrick.template.web.JetWebEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.service.ServiceFactory;
import com.techeffic.blog.util.StringUtil;

/**
 * 基础控制器
 * 
 * @author k42jc
 *
 */
@ControllerAdvice
public class BaseController {
	
	protected WebContext webCtx;
	protected WebResponse webResponse;
	
	private ServiceFactory serviceFactory;
	
	public ServiceFactory getServiceFactory() {
		return serviceFactory;
	}
	@Autowired
	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/**
	 * 用于controller下所有@requestMapping注解下的异常回调处理
	 * 
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String handlerControllerException() {
		return "redirect:/50x.html";
	}
	
	@ModelAttribute
	public void init(HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(webResponse == null){
			webResponse = new WebResponse();
		}else{
			webResponse.clear();
		}
		if(webCtx == null){
			webCtx = new WebContext(request, response);
		}else{
			webCtx.setRequest(request);
			webCtx.setResponse(response);
		}
	}
	
	

}
