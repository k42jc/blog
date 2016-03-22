package com.techeffic.blog.dao.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * jdbc基础dao
 * @author k42jc
 *
 * @param <E>
 */
public abstract class BaseJdbcDao<E>{
	
	private SimpleJdbcTemplate jdbcTemplate;

	public void delete(E e) {
		
	}

	public String saveOrUpdate(E e) {
		// TODO Auto-generated method stub
		return null;
	}

	public E findById(Class<E> clazz, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<E> findAll(Class<E> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
//	@Autowired
	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	

}
