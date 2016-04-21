package com.techeffic.blog.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.techeffic.blog.annotation.IsLogined;
import com.techeffic.blog.context.WebContext;

/**
 * 登录检查拦截器
 * @author xudong_liao<br/>
 * @date 2016年4月21日<br/>
 */
public class LoginInterceptor extends BaseInterceptor{

	@Override
	public boolean preHandle(WebContext webCtx, Object handler) {
		HandlerMethod method = (HandlerMethod)handler;
		//如果当前springMVC请求包含登录校验注解
		if(method.getMethodAnnotation(IsLogined.class) != null){
			System.out.println("请求header头部开始---------");
			System.out.println(webCtx.getRequest().getHeaders());
			webCtx.getRequest().getHeaders().forEach((key,value) -> {
				System.out.println(key+" --- " +value);
			});
			String requestType = webCtx.getRequest().getHeader("x-requested-with");
			String contentType = webCtx.getRequest().getHeader("content-type");
			System.out.println(requestType);
			System.out.println("请求header头部结束---------");
			//若请求是异步 无法完成正常的跳转 则弹窗提示
			if(webCtx.getLoginState().isLogin()){//若已登录 不操作
				return true;
			}
			try {
				if(method.getMethodAnnotation(ResponseBody.class) != null){//返回json
					webCtx.getResponse().getWriter().write("");
				}else{
					webCtx.getResponse().sendRedirect(webCtx.getRequest().getContextPath()+"/login?redirectURI="+webCtx.getRequest().getRequestURI());
				}
				return false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
