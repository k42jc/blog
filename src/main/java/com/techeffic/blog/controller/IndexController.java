package com.techeffic.blog.controller;

import java.io.IOException;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.runtime.InterpretContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{
	
	@RequestMapping("index")
	public String index() throws IOException{
		//获取模板
		JetTemplate template = this.webCtx.getTemplate();
		
		return "index";
		//获取模板数据
//		Map<String,Object> pageData = this.getServiceFactory().getTemplateService().findPageData("index");
//		webCtx.getResponse().getWriter().write(this.pageTemplateEngine.render(template, pageData));
	}
}
