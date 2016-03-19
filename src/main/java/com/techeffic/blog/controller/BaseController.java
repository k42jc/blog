package com.techeffic.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.service.ServiceFactory;

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
	@Autowired
	protected ServiceFactory serviceFactory;

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
	public void init(HttpServletRequest request,HttpServletResponse response){
		if(webCtx == null){
			webCtx = new WebContext(request, response);
		}else{
			webCtx.setRequest(request);
			webCtx.setResponse(response);
		}
	}
	

}
