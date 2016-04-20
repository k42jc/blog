package com.techeffic.blog.service.impl;

import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.IArticleService;

@Service
public class ArticleService extends BaseService implements IArticleService{

	@Override
	public WebResponse save(WebContext webCtx) {
		int type = webCtx.getReqeustAttribute().getInt("type");
		String title = webCtx.getReqeustAttribute().getString("title");
		int clazz = webCtx.getReqeustAttribute().getInt("clazz");
		String content = webCtx.getReqeustAttribute().getString("content");
		String label = webCtx.getReqeustAttribute().getString("label");
		return null;
	}

}
