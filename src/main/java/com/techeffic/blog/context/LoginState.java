package com.techeffic.blog.context;


/**
 * 登录状态对象
 * @author k42jc
 *
 */
public class LoginState {
	private String userId;
	private boolean isLogin;
	private WebContext webCtx;
	
	public LoginState(WebContext webCtx){
		this.webCtx = webCtx;
		this.userId = LoginCookie.getCookie(webCtx, "userId");
		this.isLogin = (this.userId != null);
	}
	public String getUserId() {
		return userId;
	}
	public boolean isLogin() {
		return isLogin;
	}
	/*public void userLogin(String userId) {
		LoginCookie.addCookie(webCtx, "userId", userId);
	}*/
	
}
