package com.techeffic.blog.dal.entity.mongo;

import java.util.Date;

//用户操作日志
public class Log extends IdEntity{

	private static final long serialVersionUID = -8314607084983540574L;
	//操作人
	private String userId;
	//操作事件
	private String title;
	//操作信息
	private String info;
	//操作时间
	private Date time;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
		this.putProp("userId", userId);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		this.putProp("title", title);
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
		this.putProp("info", info);
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
		this.putProp("time", time);
	}
	
	

}
