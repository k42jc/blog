package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;

@Service
public class SwiperDataModelService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		return new HashMap<String, Object>();
	}

}
