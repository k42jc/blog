package com.techeffic.blog.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jetbrick.template.JetTemplate;
import jetbrick.template.web.JetWebContext;
import jetbrick.template.web.JetWebEngine;
import jetbrick.template.web.springmvc.JetTemplateView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.service.ServiceFactory;
import com.techeffic.blog.template.PageTemplateEngine;

/**
 * 基础控制器
 * 
 * @author k42jc
 *
 */
@ControllerAdvice
public class BaseController {
//	@Value("#{setting['template']}")
	public String template = "/WEB-INF/views/template/template.html";
	protected WebContext webCtx;
	protected WebResponse webResponse;
	private ServiceFactory serviceFactory;
	protected PageTemplateEngine pageTemplateEngine;

	public PageTemplateEngine getPageTemplateEngine() {
		return pageTemplateEngine;
	}
	@Autowired
	public void setPageTemplateEngine(PageTemplateEngine pageTemplateEngine) {
		this.pageTemplateEngine = pageTemplateEngine;
	}
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
		if(webCtx == null){
			webCtx = new WebContext(request, response);
		}else{
			webCtx.setRequest(request);
			webCtx.setResponse(response);
		}
		//设置模板数据
//		pageTemplateEngine.setJetEngine(jetWebEngine.getEngine());
		JetWebContext context = new JetWebContext(request, response);
		JetTemplate template = JetWebEngine.getEngine().getTemplate(this.template);
		template.render(context, response.getOutputStream());
//		webCtx.setTemplate(pageTemplateEngine.getTemplate(template));E:\work_kingdee\blog
		
	}
	

}
