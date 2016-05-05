package com.techeffic.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techeffic.blog.dao.DaoFactory;
@Service
public class BaseService {
	private DaoFactory daoFactory;

	public DaoFactory getDaoFactory() {
		return daoFactory;
	}
	@Autowired
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
