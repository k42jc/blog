package com.techeffic.blog.service;

import java.util.Map;

public interface ITemplateService {

	Map<String, Object> findPageData(String string);
	
}
