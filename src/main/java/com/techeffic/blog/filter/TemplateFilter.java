package com.techeffic.blog.filter;

import java.io.IOException;
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

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.dao.DaoFactory;
import com.techeffic.blog.entity.Component;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.service.ServiceFactory;
import com.techeffic.blog.service.component.IComponentService;
import com.techeffic.blog.util.TemplateUtil;

/**
 * 基础模板渲染过滤器
 * @author k42jc
 *
 */
public class TemplateFilter implements Filter{
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		String requestURI = request.getRequestURI();
		//定义过滤规则
		if(null == requestURI ||"".equals(requestURI) || "/".equals(requestURI)){
			chain.doFilter(req, res);
			return;
		}/*if("/404.html".equals(requestURI)){//404 50x页面由nginx处理
			return;
		}if("/50x.html".equals(requestURI)){
			return;
		}*/
		try {
			//模板渲染
			TemplateUtil.render(new WebContext(request, response));
		} catch (Exception e) {
			response.sendRedirect("/404.html");
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
