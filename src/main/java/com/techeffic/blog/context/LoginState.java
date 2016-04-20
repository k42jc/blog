package com.techeffic.blog.context;


/**
 * 登录状态对象
 * @author k42jc
 *
 */
public class LoginState {
	private String userId;
	private boolean isLogin;
	
	public LoginState(WebContext webCtx){
		this.userId = LoginCookie.getCookie(webCtx, "userId");
		this.isLogin = (this.userId != null);
	}
	public String getUserId() {
		return userId;
	}
	public boolean isLogin() {
		return isLogin;
	}
	
}