package com.techeffic.blog.dao.mongodb;

import org.springframework.stereotype.Repository;

import com.techeffic.blog.dao.ILogDao;
import com.techeffic.blog.entity.Log;
@Repository
public class LogMongoDao extends BaseMongoDao<Log> implements ILogDao{

	@Override
	public void log(Log log) {
		this.saveOrUpdate(log);
	}
	
}
