package com.techeffic.blog.interceptors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jetbrick.template.JetTemplate;
import jetbrick.template.web.JetWebEngine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.service.ServiceFactory;

public class TemplateInterceptors implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		if(null == requestURI ||"".equals(requestURI) || "/".equals(requestURI)){
			return true;
		}
		//获取基础模板
		JetTemplate metaPageTemplate = JetWebEngine.getEngine().getTemplate("/template/template.html");
		//根据uri查询出模板配置
		Template template = ((ServiceFactory)SpringContextHolder.getBean("serviceFactory")).getTemplateService().findTemplateByRequestURI(requestURI);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("title", template.getTitle());
		resultMap.put("keywords", template.getKeyWords());
		resultMap.put("description", template.getDescription());
		resultMap.put("currentPageKey", requestURI);
		//如果当前页面不需要登录
		if(!Constants.NEED_LOGIN.equals(template.getNeedLogin())){
			//直接渲染页面
			metaPageTemplate.render(resultMap, response.getOutputStream());
			return true;
		}else{
			
		}
		return false;
	}
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
