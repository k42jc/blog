package com.techeffic.blog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统类型
 * 
 * @author k42jc
 *
 */
public class SysData extends IdEntity implements Serializable {
	private static final long serialVersionUID = 969953959641510380L;
	// 类型
	private String type;
	// 编码
	private String key;
	// 值
	private String value;
	// 顺序
	private Integer orders;
	// 备注
	private String remark;
	// 状态 0 启用 1 禁用 默认0
	private Integer status = 0;
	// 创建日期 默认当前
	private Date createDate = new Date();
	// 更新日期
	private Date updateDate;
	
	public SysData() {
		this.getProps().clear();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
		this.putProp("remark", remark);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
		this.putProp("key", key);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		this.putProp("type", type);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		this.putProp("value", value);
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
		this.putProp("orders", orders);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.putProp("status", status);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		this.putProp("createDate", createDate);
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
		this.putProp("updateDate", updateDate);
	}
}