package com.techeffic.blog.service;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.Page;
import com.techeffic.blog.entity.PageCondition;

public interface IArticleService {
	
	public Article save(WebContext webCtx);
	
	/**
	 * 根据id删除文章
	 * @param webCtx
	 */
	public void deleteById(WebContext webCtx);
	
	public Page<Article> pagenation(Class<Article> class1, Integer int1,
			Integer int2, PageCondition condition);
	
	public Article findTitleKeywordsByOrder(Integer order);

	public WebResponse findById(String string);

	public Article findTitleKeywordsById(String articleId);
	
}
