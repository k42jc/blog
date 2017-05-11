package com.techeffic.blog.dal.entity;

import org.springframework.data.domain.Sort.Direction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询条件封装实体
 * 1、查询条件封装为map->key代表条件列 value代表对应的条件值
 * 2、排序方式为spring-mongo中的Direction枚举 分别设置值为Direction.ASC/Direction.DESC表示正序/倒序
 * 3、需要排序的列为sortColumns，如果是多个则使用逗号","分隔
 * @author k42jc
 *
 */
public class PageCondition implements Serializable{

	private static final long serialVersionUID = 578656099422574298L;
	
	//查询条件
	private Map<String,Object> condition = new HashMap<String, Object>();
	//排序方式 Direction.ASC/Direction.DESC
	private Direction sortWay;
	//排序条件 可能有多个 多个使用","分隔
	private String sortColumns;
	public Object getCondition(String key) {
		return condition.get(key);
	}
	public Map<String,Object> getCondtion(){
		return this.condition;
	}
	public void setCondition(String key,Object value) {
		this.condition.put(key, value);
	}
	public String getSortColumns() {
		return sortColumns;
	}
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	public Direction getSortWay() {
		return sortWay;
	}
	public void setSortWay(Direction sortWay) {
		this.sortWay = sortWay;
	}
	
}
