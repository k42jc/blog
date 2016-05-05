package com.techeffic.blog.service;

import com.techeffic.blog.entity.Template;

public interface ITemplateService {

	public Template findTemplateByRequestURI(String requestURI);
}
