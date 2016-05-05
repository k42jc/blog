package com.techeffic.blog.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techeffic.blog.dao.IBaseDao;
import com.techeffic.blog.entity.User;

/**
 * mongodb基础dao 提供mongoTemplate实例
 * 
 * @author k42jc
 *
 * @param <E>
 *            对应的实体类对象
 */
@Repository
public class BaseMongoDao<E> implements IBaseDao<E>{

	private MongoTemplate mongoTemplate;
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 新增或修改记录 数据存在时更新，不存在时新增
	 * 
	 * @param e
	 *            实体对象
	 */
	@Override
	public void saveOrUpdate(E e) {
		if (!this.mongoTemplate.exists(new Query(new Criteria(e.getClass()
				.getSimpleName().toLowerCase())), e.getClass())) {
			this.mongoTemplate.insert(e);
		} else {
			this.mongoTemplate.save(e);
		}
	}

	/**
	 * 删除记录
	 * 
	 * @param e
	 *            待删除的对象
	 */
	@Override
	public void remove(E e) {
		this.mongoTemplate.remove(e);
	}

	/**
	 * 根据id查询实体对象
	 * 
	 * @param clazz
	 *            实体对应的类型
	 * @param id
	 *            主键
	 * @return 查询结果
	 */
	@Override
	public E findById(Class<E> clazz, String id) {
		return this.mongoTemplate.findById(id, clazz);
	}

	/**
	 * 查询所有结果集
	 * 
	 * @param clazz
	 *            实体对应的类型
	 * @return 实体结果集
	 */
	@Override
	public List<E> findAll(Class<E> clazz) {
		return this.mongoTemplate.findAll(clazz);
	}

	@Override
	public void save(E e, String name) {
		this.mongoTemplate.save(e, name);
	}
	
	@Override
	public void clear(Class<E> e) {
		this.mongoTemplate.dropCollection(e);
	}
	
	public E findOne(Query query,Class<E> e){
		return this.mongoTemplate.findOne(query, e);
	}
	
	public List<E> find(Query query,Class<E> e){
		return this.mongoTemplate.find(query, e);
	}

}
