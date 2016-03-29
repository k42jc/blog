package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.WebContext;
@Service
public class RecommendDataModelService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("blogTitle", "开发日志：遇到的问题");
		return resultMap;
	}

}
