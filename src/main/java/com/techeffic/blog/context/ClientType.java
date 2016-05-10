package com.techeffic.blog.context;

import javax.servlet.http.HttpServletRequest;

import com.techeffic.blog.util.ClientUtil;

/**
 * 存放客户端访问类型 pc端/移动端
 * 
 * @author k42jc
 *
 */
public class ClientType {
	public static final String CLIENT_TYPE_PC = "P";
	public static final String CLIENT_TYPE_MOBILE = "M";
	private WebContext webCtx;

	public ClientType(WebContext webCtx){
		this.webCtx = webCtx;
	}
	
	/**
	 * 重写toString方法 可以直接使用toString()返回客户端类型
	 */
	@Override
	public String toString() {
		if(ClientUtil.judgeIsMoblie(webCtx.getRequest()))
			return ClientType.CLIENT_TYPE_MOBILE;
		return ClientType.CLIENT_TYPE_PC;
	}

}
