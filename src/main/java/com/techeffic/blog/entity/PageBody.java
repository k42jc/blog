package com.techeffic.blog.entity;

import java.io.Serializable;

/**
 * 页面主体实体类
 * @author k42jc
 *
 */
public class PageBody implements Serializable{
	private static final long serialVersionUID = -8998571044888988578L;
	//主键
	private String id;
	//页面请求URI
	private String requestURI;
	//模板位置
	private String templatePath;
	//当前页面是否需要登录
	private Character needLogin;
	//页面标题
	private String title;
	//页面关键字
	private String keyWords;
	//页面描述
	private String description;
	//静态文件引入 包括CSS/javaScript外部文件
	private String introduce;
	
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
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public Character getNeedLogin() {
		return needLogin;
	}
	public void setNeedLogin(Character needLogin) {
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
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	
	
}
