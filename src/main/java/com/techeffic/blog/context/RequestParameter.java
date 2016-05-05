package com.techeffic.blog.context;

/**
 * 请求参数封装对象
 * @author k42jc
 *
 */
public class RequestParameter {
	private WebContext webCtx;
	
	public RequestParameter(WebContext webCtx) {
		this.webCtx = webCtx;
	}
	
	public Integer getInt(String params){
		String result = webCtx.getRequest().getParameter(params);
		if(result == null){
			return null;
		}
		return Integer.valueOf(result);
	}
	
	public Long getLong(String params){
		String result = webCtx.getRequest().getParameter(params);
		if(result == null){
			return null;
		}
		return Long.valueOf(result);
	}
	
	public String getString(String params){
		String result = webCtx.getRequest().getParameter(params);
		if(result == null){
			return null;
		}
		return result.toString();
	}
	
	public Double getDouble(String params){
		String result = webCtx.getRequest().getParameter(params);
		if(result == null){
			return null;
		}
		return Double.valueOf(result);
	}
}
