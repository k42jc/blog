package com.techeffic.blog.context;

/**
 * 分装request主动设置的参数对象
 * @author k42jc
 *
 */
public class RequestAttribute {
	private WebContext webCtx;

	public RequestAttribute(WebContext webCtx) {
		this.webCtx = webCtx;
	}

	public Integer getInt(String params) {
		Object result = getObject(params);
		if (result == null) {
			return null;
		}
		return Integer.valueOf(result.toString());
	}
	
	public Object getObject(String params){
		return webCtx.getRequest().getSession().getAttribute(params);
	}

	public Long getLong(String params) {
		Object result = getObject(params);
		if (result == null) {
			return null;
		}
		return Long.valueOf(result.toString());
	}

	public String getString(String params) {
		Object result = getObject(params);
		if (result == null) {
			return null;
		}
		return result.toString();
	}

	public Double getDouble(String params) {
		Object result = getObject(params);
		if (result == null) {
			return null;
		}
		return Double.valueOf(result.toString());
	}
}
