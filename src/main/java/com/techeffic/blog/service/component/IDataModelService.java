package com.techeffic.blog.service.component;

import java.util.Map;

import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.entity.Component;


public interface IDataModelService {
	Map<String,Object> getData(WebContext webCtx);
}
