package com.techeffic.blog.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jetbrick.template.JetTemplate;
import jetbrick.template.web.JetWebEngine;
import jetbrick.util.JSONUtils;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.dao.DaoFactory;
import com.techeffic.blog.entity.Component;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.exception.ServiceException;
import com.techeffic.blog.service.ServiceFactory;
import com.techeffic.blog.util.TemplateUtil;

/**
 * 基础模板渲染过滤器
 * @author k42jc
 *
 */
public class TemplateFilter implements Filter{
	
	private ServiceFactory serviceFactory;
	private String requestURI;
	private WebContext webCtx;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		requestURI = ((HttpServletRequest)req).getRequestURI();
		//定义过滤规则并处理requestURI
		if(!filterRules(requestURI)){
			chain.doFilter(req, res);
			return;
		}
		webCtx = new WebContext((HttpServletRequest)req, (HttpServletResponse)res);
		try {
			//获取对应页面并渲染
			render((HttpServletRequest)req,(HttpServletResponse)res);
		} catch (Exception e) {
			((HttpServletResponse)res).sendRedirect("/404.html");
		}
		
	}
	
	/**
	 * 设置过滤规则 某些URI无需过滤
	 * @param requestURI 请求URI
	 * @return true需要过滤当前请求 false表示通过
	 */
	private boolean filterRules(String requestURI) {
		//基础页面无需过滤 nginx会将此指定为首页
		if("/".equals(requestURI))
			return false;
		//*.action请求交给springMVC处理
		if(requestURI.endsWith(".action"))
			return false;
		return true;
	}

	private void render(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// 获取对应请求模板数据
		Template template = serviceFactory.getTemplateService()
				.findTemplateByRequestURI(requestURI);
		// 填充页面数据
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("title", template.getTitle());
		datas.put("keywords", template.getKeyWords());
		datas.put("description", template.getDescription());
		//将请求中携带的参数放入模板数据
		request.getParameterMap().forEach((key,values) ->{
			if(values.length == 1){
				datas.put(key,values[0]);
			}else{
				datas.put(key, JSONUtils.toJSONString(values));
			}
		});
		// 如果当前页面不需要登录
		boolean isFilter = false;
		if (Constants.NEED_LOGIN.equals(template.getNeedLogin())) {
			//需要登录则查看当前用户是否已登录 
			if(!webCtx.getLoginState().isLogin()){
				isFilter = true;
				//否则跳转到登录页面
				response.sendRedirect("/login?redirectURI="+requestURI);
			}
		}
		if(isFilter){
			return;
		}else{
			// 直接渲染页面
			OutputStream outputStream =	TemplateUtil.render(response.getOutputStream(), template.getPath(), datas);
			outputStream.flush();
			outputStream.close();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		serviceFactory = (ServiceFactory) SpringContextHolder.getBean("serviceFactory");
	}

}
