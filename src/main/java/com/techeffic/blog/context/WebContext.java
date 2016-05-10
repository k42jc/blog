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
	private RequestParameter requestParameter;
	private RequestAttribute requestAttribute;
	private ClientType clientType;
	
	public ClientType getClientType() {
		return clientType;
	}

	public RequestAttribute getRequestAttribute() {
		return requestAttribute;
	}

	public LoginState getLoginState() {
		return loginState;
	}

	public RequestParameter getRequestParameter() {
		return requestParameter;
	}

	private WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = new Request(request);
		this.response = (response == null?null:new Response(response));
		loginState = new LoginState(this);
		requestParameter = new RequestParameter(this);
		requestAttribute = new RequestAttribute(this);
		clientType = new ClientType(this);
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
	
	/**
	 * 用户登录
	 * @param userId
	 */
	/*public void userLogin(String userId){
		LoginCookie.addCookie(this, "userId", userId);
	}*/
	
}
