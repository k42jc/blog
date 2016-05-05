package com.techeffic.blog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章实体类
 * 
 * @author k42jc
 *
 */
public class Article implements Serializable {
	private static final long serialVersionUID = -1029830130241178918L;
	// 主键
	private String id;
	// 渲染文本格式 html或者markdown 默认html
	private String viewType = "html";
	// 标题
	private String title;
	// 博客类型 Y 原创 or Z 转载 默认原创
	private String type = "Y";
	// 博客分类
	private String clazz;
	// 所属用户
	private String userId;
	// 关键字/标签
	private String keywords;
	// 浏览量
	private Integer viewNums = 0;
	// 创建日期 默认当前
	private Date createDate = new Date();
	// 更新日期
	private Date updateDate;
	// 预览图片 默认为空
	private String viewImgUrl = "";
	// 预览文字
	private String contentView;
	// html类型文本内容
	private String contentHtml;
	// markdown格式文本内容
	private String contentMarkdown;
	// 关联用户
	private User user;
	// 顺序 用于页面URL显示用
	private Integer order;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getViewNums() {
		return viewNums;
	}

	public void setViewNums(Integer viewNums) {
		this.viewNums = viewNums;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getViewImgUrl() {
		return viewImgUrl;
	}

	public void setViewImgUrl(String viewImgUrl) {
		this.viewImgUrl = viewImgUrl;
	}

	public String getContentView() {
		return contentView;
	}

	public void setContentView(String contentView) {
		this.contentView = contentView;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public String getContentMarkdown() {
		return contentMarkdown;
	}

	public void setContentMarkdown(String contentMarkdown) {
		this.contentMarkdown = contentMarkdown;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}