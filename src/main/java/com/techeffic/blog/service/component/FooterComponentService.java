package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.WebContext;

/**
 * 用于页面底部数据获取
 * @author k42jc
 *
 */
@Service
public class FooterComponentService implements IComponentService{
	
	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("info", "廖旭东个人博客");
		return resultMap;
	}
}
