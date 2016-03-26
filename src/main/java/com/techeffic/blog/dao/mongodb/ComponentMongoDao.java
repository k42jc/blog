package com.techeffic.blog.dao.mongodb;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techeffic.blog.dao.IComponentDao;
import com.techeffic.blog.entity.Component;
/**
 * 组件dao
 * @author k42jc
 *
 */
@Repository
public class ComponentMongoDao extends BaseMongoDao<Component> implements IComponentDao{

	@Override
	public Component findComponentByKey(String key) {
		return this.getMongoTemplate().findOne(new Query(new Criteria("key").is(key)), Component.class);
	}

}
