package com.techeffic.blog.context;

import java.util.Arrays;

import javax.servlet.http.Cookie;

/**
 * 封装cookie用于登录操作
 * @author k42jc
 *
 */
public class LoginCookie {
	
	private static int defaultTime = 7*24*60*60;
	
	/**
	 * 添加Cookie
	 * @param webCtx
	 */
	public static void addCookie(WebContext webCtx,String key,String value){
		Cookie loginCookie = new Cookie(key, value);
		loginCookie.setMaxAge(defaultTime);//保存7天
		loginCookie.setPath("/");//将cookie保存在根路径 便于其它请求访问
		webCtx.getResponse().addCookie(loginCookie);
	}
	
	/**
	 * 获取cookie键为key的值
	 * @param webCtx
	 * @param key
	 * @return
	 */
	public static String getCookie(WebContext webCtx,String key){
		String value = null;
		Cookie[] cookies = webCtx.getRequest().getCookies();
		if(cookies == null){
			System.out.println(webCtx.getRequest().getRequestURI()+"---cookie为空");
			return value;
		}else{
			System.out.println(webCtx.getRequest().getRequestURI()+"---cookie不为空");
		}
		for(int i=0;i<cookies.length;i++){
			if(key.equals(cookies[i].getName())){
				value = cookies[i].getValue();
				break;
			}
		}
		return value;
	}
	
	/**
	 * 清空cookie
	 * @param webCtx
	 */
	public static void clearCookie(WebContext webCtx){
		Cookie[] cookies = webCtx.getRequest().getCookies();
		Arrays.asList(cookies).forEach(cookie -> {
			cookie.setMaxAge(0);
			webCtx.getResponse().addCookie(cookie);
		});
	}
}
