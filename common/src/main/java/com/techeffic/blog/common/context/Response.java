package com.techeffic.blog.common.context;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 封装httpServletResponse
 * @author xudong_liao<br/>
 * @date 2016年4月21日<br/>
 */
public final class Response extends HttpServletResponseWrapper {

	public Response(HttpServletResponse response) {
		super(response);
	}

}
