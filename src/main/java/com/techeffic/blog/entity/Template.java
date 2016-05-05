package com.techeffic.blog.entity;

import java.io.Serializable;

/**
 * 模板配置实体 
 * 针对页面标题/关键字/描述等字段，按页面不同，默认按数据库配置，根据文章不同可由程序控制 
 * @author k42jc
 *
 */
public class Template implements Serializable{
	private static final long serialVersionUID = -2408389530061923159L;
	//主键
	private String id;
	//请求URI
	private String requestURI;
	//页面标题
	private String title;
	//页面关键字
	private String keyWords;
	//页面描述
	private String description;
	//模板位置
	private String path;
	//是否需要登录
	private String needLogin;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	public String getNeedLogin() {
		return needLogin;
	}
	public void setNeedLogin(String needLogin) {
		this.needLogin = needLogin;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
