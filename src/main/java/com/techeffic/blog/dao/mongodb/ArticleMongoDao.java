package com.techeffic.blog.dao.mongodb;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.techeffic.blog.dao.IArticleDao;
import com.techeffic.blog.entity.Article;

@Repository
public class ArticleMongoDao extends BaseMongoDao<Article> implements IArticleDao{
	
	/**
	 * 找到上一篇文章
	 */
	@Override
	public Article findPrevious() {
		return getMongoTemplate().findOne(new Query().with(new Sort(Sort.Direction.DESC,"createDate")),Article.class);
	}

	@Override
	public Article findById(String id) {
		//需要更新文章被查看数量
		Update update = new Update();
		update.inc("viewNums", 1);
		return getMongoTemplate().findAndModify(new Query(new Criteria("id").is(id)), update, Article.class);
	}

	@Override
	public List<Article> findViewedTopArticles(int n) {
		DBObject fields = new BasicDBObject();
		fields.put("title", 1);
		fields.put("viewNums", 1);
		
		DBObject queryObject = new BasicDBObject();
		
		Query query = new BasicQuery(queryObject, fields);
		return this.getMongoTemplate().find(query.with(new Sort(Sort.Direction.DESC,"viewNums")).limit(n), Article.class);
	}

	@Override
	public List<Article> findRandomArticles(int n) {
		DBObject fields = new BasicDBObject();
		fields.put("title", 1);
		fields.put("type", 1);
		fields.put("clazz", 1);
		fields.put("createDate", 1);
		fields.put("viewImgUrl", 1);
		fields.put("contentView", 1);
		
		DBObject queryObject = new BasicDBObject();
		
		Query query = new BasicQuery(queryObject, fields);
		int count = (int)this.getMongoTemplate().count(new Query(), Article.class)-n;
		return this.getMongoTemplate().find(query.skip(RandomUtils.nextInt(count>=0?count:1)).limit(n), Article.class);
	}

	@Override
	public Long findNumsByClazz(String key) {
		return this.getMongoTemplate().count(new Query(new Criteria("clazz").is(key)), Article.class);
	}

	@Override
	public List<Article> findBySearch(String content) {
		Pattern pattern = Pattern.compile("^.*"+content+".*$", Pattern.CASE_INSENSITIVE);
		return this.getMongoTemplate().find(new Query(new Criteria().orOperator(Criteria.where("title").regex(pattern),Criteria.where("contentMarkdown").regex(pattern))), Article.class);
	}

	@Override
	public List<Article> findAllIds() {
		DBObject fields = new BasicDBObject();
		fields.put("id", 1);
		
		DBObject queryObject = new BasicDBObject();
		Query query = new BasicQuery(queryObject, fields);
		return this.getMongoTemplate().find(query, Article.class);
	}

}
