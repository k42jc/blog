package com.techeffic.blog.dal.dao;


import com.techeffic.blog.dal.entity.mongo.Component;

public interface IComponentDao extends IBaseDao<Component>{
	
	Component findComponentByKey(String key);
}
