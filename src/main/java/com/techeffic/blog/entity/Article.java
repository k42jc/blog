package com.techeffic.blog.entity;

import java.util.Date;

public class Article {
    private Long id;
    //渲染文本格式 html或者markdown
    private String contentType;

    private String title;

    private Long typeId;

    private Long userId;

    private String keywords;

    private Integer viewNums;

    private Date createDate;

    private Date updateDate;

    private String contentView;

    private String contentHtml;
    private String contentMarkdown;
    
    private SysData blogType;
    
    private User user;
    
    public SysData getBlogType() {
		return blogType;
	}

	public void setBlogType(SysData blogType) {
		this.blogType = blogType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long typeId) {
        this.typeId = typeId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Integer getViewNums() {
        return viewNums;
    }

    public void setViewNums(Integer viewNums) {
        this.viewNums = viewNums;
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

    public String getContentView() {
        return contentView;
    }

    public void setContentView(String contentView) {
        this.contentView = contentView == null ? null : contentView.trim();
    }

}