package com.techeffic.blog.service;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;

public interface IArticleService {
	
	public WebResponse save(WebContext webCtx);
}
