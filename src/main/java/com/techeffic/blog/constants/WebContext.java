package com.techeffic.blog.constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jetbrick.template.JetTemplate;

public class WebContext {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private JetTemplate template;
	
	public JetTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JetTemplate template) {
		this.template = template;
	}

	public WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public Integer getInt(String params){
		return Integer.valueOf(this.request.getParameter(params).toString());
	}
	
	public Long getLong(String params){
		return Long.valueOf(this.request.getParameter(params).toString());
	}
	
	public String getString(String params){
		return this.request.getParameter(params).toString();
	}
	
	public Double getDouble(String params){
		return Double.valueOf(this.request.getParameter(params).toString());
	}

}
