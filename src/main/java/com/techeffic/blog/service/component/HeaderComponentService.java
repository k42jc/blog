package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.service.BaseService;

@Service
public class HeaderComponentService extends BaseService implements IComponentService{

	@Override
	public Map<String, Object> getData() {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("title", "廖旭东个人博客");
		return resultMap;
	}

}
