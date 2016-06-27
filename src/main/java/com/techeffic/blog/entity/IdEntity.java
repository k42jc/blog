package com.techeffic.blog.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
