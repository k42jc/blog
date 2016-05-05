package com.techeffic.blog.service.impl;

import org.springframework.stereotype.Service;

import com.techeffic.blog.entity.Component;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.IComponentService;

@Service
public class ComponentService extends BaseService implements IComponentService{

	@Override
	public Component findComponentByKey(String key) {
		return this.getDaoFactory().getComponentMongoDao().findComponentByKey(key);
	}

}
