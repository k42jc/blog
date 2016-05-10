package com.techeffic.blog.service;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;

public interface IArticleService {
	
	public Article save(WebContext webCtx);
	
	/**
	 * 根据id删除文章
	 * @param webCtx
	 */
	public void deleteById(WebContext webCtx);
	
	/**
	 * 根据order查找文章
	 * @param int1
	 * @return
	 */
	public WebResponse findByOrder(Integer int1);
}
