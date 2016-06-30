package com.techeffic.blog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.ServiceFactory;

/**
 * 基础过滤器 用于分发请求
 * 
 * @author k42jc
 *
 */
public abstract class BaseDispatcher implements Filter {
	protected volatile String requestURI;
	protected volatile WebContext webCtx;
	protected ServiceFactory serviceFactory;
	protected volatile HttpServletRequest request;
	protected volatile HttpServletResponse response;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		request = ((HttpServletRequest) req);
		response = ((HttpServletResponse) res);
		requestURI = ((HttpServletRequest) req).getRequestURI();
		// 定义过滤规则并处理requestURI
		if (!doFilter()) {
			chain.doFilter(req, res);
			return;
		}
		webCtx = WebContext.init(request, response);
		if (isLoginPageAndLogined()) {
			webCtx.getResponse().sendRedirect("/");
		}
		dispatcher();
	}

	// 判断是否已登录
	private boolean isLoginPageAndLogined() {
		if (requestURI.indexOf("/login") >= 0) {
			if (webCtx.getLoginState().isLogin()) {
				return true;
			}
		}
		return false;
	}

	private boolean doFilter() {
		// 基础页面无需过滤 nginx会将此指定为首页
		if ("/".equals(requestURI))
			return false;
		// *.action请求交给springMVC处理
		// 其它带"."的请求如图片加载项都不经过java处理
		if (requestURI.indexOf(".") >= 0)
			return false;
		return true;
	}

	public abstract void dispatcher() throws IOException;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		serviceFactory = (ServiceFactory) SpringContextHolder
				.getBean("serviceFactory");
	}

}
