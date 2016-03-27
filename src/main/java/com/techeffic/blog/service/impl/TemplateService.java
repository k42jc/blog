package com.techeffic.blog.service.impl;

import org.springframework.stereotype.Service;

import com.techeffic.blog.entity.Template;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.ITemplateService;

@Service
public class TemplateService extends BaseService implements ITemplateService{

	@Override
	public Template findTemplateByRequestURI(String requestURI) {
		return this.getDaoFactory().getTemplateMongoDao().findTemplateByRequestURI(requestURI);
	}
	
	

}
