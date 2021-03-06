package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.BaseService;

@Service
public class HeaderDataModelService extends BaseService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("title", "廖旭东个人博客");
		//若用户已登录 头部显示用户信息 而不是登录
		if(webCtx.getLoginState().isLogin()){
			resultMap.put("userName", "test");
		}
		return resultMap;
	}

}
