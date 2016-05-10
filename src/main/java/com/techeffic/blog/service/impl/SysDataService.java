package com.techeffic.blog.service.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.techeffic.blog.entity.SysData;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.ISysDataService;

@Service
public class SysDataService extends BaseService implements ISysDataService{

	@Override
	public SysData findByKey(String key) {
		return this.getDaoFactory().getSysDataMongoDao().findOne(new Query(new Criteria("key").is(key)), SysData.class);
	}

}
