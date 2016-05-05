package com.techeffic.blog.dao.jdbc;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * jdbc基础dao
 * @author k42jc
 *
 * @param <E>
 */
public abstract class BaseJdbcDao<E>{
	private NamedParameterJdbcTemplate jdbcTemplate;

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

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
//	@Autowired
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}
