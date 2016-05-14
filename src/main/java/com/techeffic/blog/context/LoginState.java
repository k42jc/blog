package com.techeffic.blog.context;


/**
 * 登录状态对象
 * @author k42jc
 *
 */
public class LoginState {
	private static String userId;
	private static boolean isLogin;
	private WebContext webCtx;
	
	public LoginState(WebContext webCtx){
		this.webCtx = webCtx;
		userId = LoginCookie.getCookie(webCtx, "userId");
		isLogin = (userId != null);
	}
	public String getUserId() {
		return userId;
	}
	public boolean isLogin() {
		return isLogin;
	}
	/**
	 * 用户登录操作
	 * @param userId
	 */
	public void userLogin(String userId) {
		LoginCookie.addCookie(webCtx, "userId", userId);
	}
	
}
