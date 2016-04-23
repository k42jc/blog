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
	private Request request;
	private Response response;
	private LoginState loginState;
	private RequestAttribute reqeustAttribute;
	
	public LoginState getLoginState() {
		return loginState;
	}

	public RequestAttribute getReqeustAttribute() {
		return reqeustAttribute;
	}

	private WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = new Request(request);
		this.response = response == null?null:new Response(response);
		loginState = new LoginState(this);
		reqeustAttribute = new RequestAttribute(this);
	}
	
	public static WebContext init(HttpServletRequest request, HttpServletResponse response){
		return new WebContext(request, response);
	}

	public Request getRequest() {
		return request;
	}


	public Response getResponse() {
		return response;
	}

	public void userLogin(String userId){
		LoginCookie.addCookie(this, "userId", userId);
	}
	
}
