package com.techeffic.blog.service.impl;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.Page;
import com.techeffic.blog.entity.PageCondition;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.IArticleService;
import com.techeffic.blog.util.DateUtil;
import com.techeffic.blog.util.KeyUtil;

@Service
public class ArticleService extends BaseService implements IArticleService{

	@Override
	public synchronized Article save(WebContext webCtx) {
		String editType = webCtx.getRequestParameter().getString("editType");
		String id = webCtx.getRequestParameter().getString("id");
		String type = webCtx.getRequestParameter().getString("type");
		String title = webCtx.getRequestParameter().getString("title");
		String clazz = webCtx.getRequestParameter().getString("clazz");
		String html = webCtx.getRequestParameter().getString("html");
		String markdown = webCtx.getRequestParameter().getString("markdown");
		String label = webCtx.getRequestParameter().getString("label");
		String contentView = webCtx.getRequestParameter().getString("contentView");
		Article article = new Article();
		if("add".equals(editType)){
			article.setId(KeyUtil.generate());
			article.setCreateDate(new Date());
			//找到上一篇文章
			/*Article previous = this.getDaoFactory().getArticleMongoDao().findPrevious();
			article.setOrder(previous == null?1:previous.getOrder()+1);*/
		}else{//更新
			article.setUpdateDate(new Date());
			article.setId(id);
		}
		article.setContentMarkdown(markdown);
		article.setContentView(contentView.length() >= 151?contentView.substring(0, 150):contentView);
		article.setContentHtml(html);
		article.setClazz(clazz);
		article.setType(type);
		article.setTitle(title);
		article.setKeywords(label);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*try {
			article.setCreateDate(sdf.parse("2016-04-18 20:36:21"));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		this.getDaoFactory().getArticleMongoDao().saveOrUpdate(article);
		return article;
	}

	@Override
	public void deleteById(WebContext webCtx) {
		Article article = new Article();
		article.setId(webCtx.getRequestParameter().getString("id"));
		this.getDaoFactory().getArticleMongoDao().remove(article);
	}

	/*@Override
	public WebResponse findById(String id) {
		//查询当前文章且显示上一篇下一篇文章
		WebResponse webResponse = new WebResponse();
		Article article = this.getDaoFactory().getArticleMongoDao().findById(id);
		webResponse.put("article", article);
		//过滤查询条件 查询指定列
		DBObject queryObject = new BasicDBObject("createDate",new BasicDBObject("$gt", article.getCreateDate()));
		queryObject.put("type", arg1)
		DBObject queryObject2 = new BasicDBObject("createDate",new BasicDBObject("$lt", article.getCreateDate()));
		DBObject filedsObject = new BasicDBObject("title", 1).append("id", 1);
		Query query1 = new BasicQuery(queryObject, filedsObject);
		Query query2 = new BasicQuery(queryObject2,filedsObject);
		Article previous = this.getDaoFactory().getArticleMongoDao().findOne(query1.with(new Sort(Sort.Direction.ASC,"createDate")), Article.class);
		Article next = this.getDaoFactory().getArticleMongoDao().findOne(query2.with(new Sort(Sort.Direction.DESC,"createDate")), Article.class);
		webResponse.put("previous", previous);
		webResponse.put("next", next);
		return webResponse;
	}*/

	@Override
	public Page<Article> pagenation(Class<Article> e, Integer page,
			Integer pageSize, PageCondition condition) {
		return this.getDaoFactory().getArticleMongoDao().pagenation(e, page, pageSize, condition);
	}
	
	@Override
	public Article findTitleKeywordsByOrder(Integer order) {
		//指定查询条件 返回指定列的查询方式
//		QueryBuilder builder = new QueryBuilder();
//		builder.is(new BasicDBObject("order",order));
		DBObject queryObject = new BasicDBObject("order",order);
		DBObject fieldsObject = new BasicDBObject();
		fieldsObject.put("title", 1);
		fieldsObject.put("keywords", 1);
		Query query = new BasicQuery(queryObject, fieldsObject);
		return this.getDaoFactory().getArticleMongoDao().findOne(query, Article.class);
	}

	@Override
	public WebResponse findById(String id) {
		//查询当前文章且显示上一篇下一篇文章
		WebResponse webResponse = new WebResponse();
		Article article = this.getDaoFactory().getArticleMongoDao().findById(id);
		webResponse.put("article", article);
		//过滤查询条件 查询指定列
		DBObject queryObject = new BasicDBObject("createDate",new BasicDBObject("$gt", article.getCreateDate()));
		queryObject.put("clazz", article.getClazz());
		DBObject queryObject2 = new BasicDBObject("createDate",new BasicDBObject("$lt", article.getCreateDate()));
		queryObject2.put("clazz", article.getClazz());
		DBObject filedsObject = new BasicDBObject("title", 1).append("id", 1);
		Query query1 = new BasicQuery(queryObject, filedsObject);
		Query query2 = new BasicQuery(queryObject2,filedsObject);
		Article previous = this.getDaoFactory().getArticleMongoDao().findOne(query1.with(new Sort(Sort.Direction.ASC,"createDate")), Article.class);
		Article next = this.getDaoFactory().getArticleMongoDao().findOne(query2.with(new Sort(Sort.Direction.DESC,"createDate")), Article.class);
		webResponse.put("previous", previous);
		webResponse.put("next", next);
		return webResponse;
	}

	@Override
	public Article findTitleKeywordsById(String id) {
		DBObject queryObject = new BasicDBObject("id",id);
		DBObject fieldsObject = new BasicDBObject();
		fieldsObject.put("title", 1);
		fieldsObject.put("keywords", 1);
		Query query = new BasicQuery(queryObject, fieldsObject);
		return this.getDaoFactory().getArticleMongoDao().findOne(query, Article.class);
	}

}
