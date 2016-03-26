package com.techeffic.blog.dao;

import com.techeffic.blog.entity.Component;

public interface IComponentDao extends IBaseDao<Component>{
	
	Component findComponentByKey(String key);
}
