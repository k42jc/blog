package com.techeffic.blog.dao.mongodb;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techeffic.blog.dao.ISysDataDao;
import com.techeffic.blog.entity.SysData;

/**
 * 基础数据持久层实现
 * @author k42jc
 *
 */
@Repository
public class SysDataMongoDao extends BaseMongoDao<SysData> implements ISysDataDao{
	@Override
	public List<SysData> findByType(String type) {
		return this.find(new Query(new Criteria("type").is(type)), SysData.class);
	}

	@Override
	public SysData findByKey(String clazz) {
		return this.getMongoTemplate().findOne(new Query(new Criteria("key").is(clazz)), SysData.class);
	}
	
	
}
