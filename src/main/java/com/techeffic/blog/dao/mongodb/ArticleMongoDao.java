package com.techeffic.blog.dao.mongodb;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techeffic.blog.dao.IArticleDao;
import com.techeffic.blog.entity.Article;

@Repository
public class ArticleMongoDao extends BaseMongoDao<Article> implements IArticleDao{
	
	/**
	 * 根据文章标识查找文章
	 * @param articleId 文章标志 系统使用文章顺序
	 */
	@Override
	public Article findByOrder(int order) {
		return getMongoTemplate().findOne(new Query(new Criteria("order").is(order)), Article.class);	
	}
	
	/**
	 * 找到上一篇文章
	 */
	@Override
	public Article findPrevious() {
		return getMongoTemplate().findOne(new Query().with(new Sort(Sort.Direction.DESC,"createDate")),Article.class);
	}

	@Override
	public Article findById(String id) {
		return getMongoTemplate().findOne(new Query(new Criteria("id").is(id)), Article.class);
	}

}
