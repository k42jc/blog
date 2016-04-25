package com.techeffic.blog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统类型
 * 
 * @author k42jc
 *
 */
public class SysData implements Serializable {
	private static final long serialVersionUID = 969953959641510380L;
	// 主键
	private String id;
	// 类型
	private String type;
	// 值
	private String value;
	// 顺序
	private Integer orders;
	// 状态 0 启用 1 禁用 默认0
	private Integer status = 0;
	// 创建日期 默认当前
	private Date createDate = new Date();
	// 更新日期
	private Date updateDate;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
}