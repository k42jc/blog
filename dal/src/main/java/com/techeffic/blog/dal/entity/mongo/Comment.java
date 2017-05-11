package com.techeffic.blog.dal.entity.mongo;

import java.util.Date;

public class Comment extends IdEntity{
	private static final long serialVersionUID = -5980658818323902984L;
	//文章id
    private String articleId;
    //用户id 提供默认空值 游客
    private String userId = "";
    //游客名
    private String userName;
    //评论内容
    private String content;
    //点赞数量 暂时无此功能 默认为0
    private Integer likeNums = 0;
    //父级评论
    private String pid;
    //创建日期
    private Date createdate;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
		this.getProps().put("userName", userName);
	}
	public String getArticleId() {
		return articleId;
	}
	public Comment() {
		this.getProps().clear();
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
		this.getProps().put("articleId", articleId);
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
		this.getProps().put("userId", userId);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
		this.getProps().put("content", content);
	}
	public Integer getLikeNums() {
		return likeNums;
	}
	public void setLikeNums(Integer likeNums) {
		this.likeNums = likeNums;
		this.getProps().put("likeNums", likeNums);
		
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
		this.getProps().put("pid", pid);
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
		this.getProps().put("createdate", createdate);
	}

}