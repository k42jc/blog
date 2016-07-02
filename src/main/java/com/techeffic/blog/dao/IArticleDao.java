package com.techeffic.blog.dao;

import com.techeffic.blog.entity.Article;

/**
 * 文章持久层接口
 * @author k42jc
 *
 */
public interface IArticleDao extends IBaseDao<Article>{
	
	/**
	 * 找到上一篇文章
	 * @return
	 */
	Article findPrevious();
	
	/**
	 * 根据id查询文章信息
	 * @param id
	 * @return
	 */
	Article findById(String id);
	
}
