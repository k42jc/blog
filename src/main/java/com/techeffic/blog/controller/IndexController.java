package com.techeffic.blog.controller;

import java.io.IOException;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.runtime.InterpretContext;
import jetbrick.template.web.JetWebEngine;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.util.StringUtil;

@Controller
@RequestMapping("index")
public class IndexController extends BaseController{
	
	@RequestMapping("")
	public void index() throws IOException{
		//获取器当前请求URI
		String uri = webCtx.getRequest().getRequestURI();
		//根据uri查询出模板
		//获取模板
		JetTemplate template = this.webCtx.getMetaPageTemplate();
		webResponse.put("title", "首页");
		webResponse.put("data", "<link rel='stylesheet' href='/static/css/kube.css'>"+
	"<link rel='stylesheet' href='/static/css/swiper.min.css'>");
		JetTemplate indexTemplate = JetWebEngine.getEngine().getTemplate("/WEB-INF/views/"+"index"+".html");
		webResponse.put("body", StringUtil.strArrayToHtml(indexTemplate.getSource().getLines()));
		template.render(webResponse, webCtx.getResponse().getOutputStream());
//		return "index";
	}
}
