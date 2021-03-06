package com.techeffic.blog.dao;

import java.util.List;

import com.mongodb.CommandResult;
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
	
	/**
	 * 查询点击排行前n位的文章
	 * @param i
	 * @return
	 */
	List<Article> findViewedTopArticles(int n);
	
	/**
	 * 查询随机前n条数据
	 * @param i
	 * @return
	 */
	List<Article> findRandomArticles(int n);
	
	/**
	 * 根据文章类型查询文章数量
	 * @param key
	 * @return
	 */
	Long findNumsByClazz(String key);
	
	/**
	 * 根据搜索内容查找相关的文章
	 * @param content 搜索关键字
	 * @return 符合查找条件的结果
	 */
	List<Article> findBySearch(String content);
	
	/**
	 * 找到所有文章的id
	 * @return
	 */
	List<Article> findAllIds();
	
	/**
	 * 查询最新的n篇文章
	 * @param n
	 * @return
	 */
	List<Article> findNewArticles(int n);
	
}
