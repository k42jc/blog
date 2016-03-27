package com.techeffic.blog.service.component;

import java.util.Map;

import com.techeffic.blog.constants.WebContext;


public interface IComponentService {
	Map<String,Object> getData(WebContext webCtx);
}
