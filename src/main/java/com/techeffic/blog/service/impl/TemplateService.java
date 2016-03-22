package com.techeffic.blog.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.dao.mongodb.BaseMongoDao;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.service.ITemplateService;

@Service
public class TemplateService extends BaseMongoDao<Template> implements ITemplateService{

	@Override
	public Map<String, Object> findPageData(String string) {
		return null;
	}

}
