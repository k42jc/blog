package com.techeffic.blog.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统参数类
 * 绑定系统常用参数
 * httpServletRequest httpServletResponse jetTemplate的元数据模板
 * @author k42jc
 *
 */
public class WebContext {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private LoginState loginState;
	private RequestAttribute reqeustAttribute;
	
	public LoginState getLoginState() {
		return loginState;
	}

	public RequestAttribute getReqeustAttribute() {
		return reqeustAttribute;
	}

	public WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		loginState = new LoginState(this);
		reqeustAttribute = new RequestAttribute(this);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void logined(String userId){
		LoginCookie.addCookie(this, "userId", userId);
	}
	
}
