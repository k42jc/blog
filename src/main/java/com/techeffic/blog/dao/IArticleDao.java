package com.techeffic.blog.dao;

import com.techeffic.blog.entity.Article;

/**
 * 文章持久层接口
 * @author k42jc
 *
 */
public interface IArticleDao extends IBaseDao<Article>{
	
	/**
	 * 根据文章编号查找
	 * @param articleId
	 * @return
	 */
	Article findByOrder(int articleId);
	
	/**
	 * 找到上一篇文章
	 * @return
	 */
	Article findPrevious();
	
}
