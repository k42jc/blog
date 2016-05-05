package com.techeffic.blog.entity;

import java.io.Serializable;

/**
 * 页面组件实体
 * @author k42jc
 *
 */
public class Component implements Serializable{
	private static final long serialVersionUID = 6385751780887960216L;
	//主键
	private String id;
	//当前组件唯一标识
	private String key;
	//当前组件所在目录
	private String path;
	//当前组件内嵌数据获取类名
	private String className;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/*public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}*/
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
	
}
