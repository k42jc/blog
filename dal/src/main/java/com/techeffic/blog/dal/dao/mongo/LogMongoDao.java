package com.techeffic.blog.dal.dao.mongo;

import com.techeffic.blog.dal.dao.ILogDao;
import com.techeffic.blog.dal.entity.mongo.Log;
import org.springframework.stereotype.Repository;

@Repository
public class LogMongoDao extends BaseMongoDao<Log> implements ILogDao {

	@Override
	public void log(Log log) {
		this.saveOrUpdate(log);
	}
	
}
