package com.techeffic.blog.service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;

public interface IArticleService {
	
	public Article save(WebContext webCtx);
	
	/**
	 * 根据id删除文章
	 * @param webCtx
	 */
	public void deleteById(WebContext webCtx);
}
