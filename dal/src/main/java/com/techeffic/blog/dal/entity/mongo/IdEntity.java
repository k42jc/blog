package com.techeffic.blog.dal.entity.mongo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于提供主键字段以及saveOrUpdate方法的mongo参数 需要在子类中继承此类
 * 且在每个属性的set方法中将元素添加到props中
 * @author k42jc
 *
 */
public class IdEntity implements Serializable{
	private static final long serialVersionUID = -429695128744153655L;
	protected String id;
	
	private Map<String,Object> props = new HashMap<String, Object>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}
	
	public void putProp(String key,Object value){
		this.props.put(key, value);
	}
	
}
