package com.techeffic.blog.service.impl;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.Page;
import com.techeffic.blog.entity.PageCondition;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.IArticleService;
import com.techeffic.blog.util.KeyUtil;

@Service
public class ArticleService extends BaseService implements IArticleService{

	@Override
	public synchronized Article save(WebContext webCtx) {
		String type = webCtx.getRequestParameter().getString("type");
		String title = webCtx.getRequestParameter().getString("title");
		String clazz = webCtx.getRequestParameter().getString("clazz");
		String html = webCtx.getRequestParameter().getString("html");
		String markdown = webCtx.getRequestParameter().getString("markdown");
		String label = webCtx.getRequestParameter().getString("label");
		String contentView = webCtx.getRequestParameter().getString("contentView");
		Article article = new Article();
		article.setId(KeyUtil.generate());
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
		article.setCreateDate(new Date());
		//找到上一篇文章
		Article previous = this.getDaoFactory().getArticleMongoDao().findPrevious();
		article.setOrder(previous == null?1:previous.getOrder()+1);
		article.setContentHtml(html);
		article.setContentMarkdown(markdown);
		article.setContentView(contentView.length() >= 151?contentView.substring(0, 150):contentView);
		this.getDaoFactory().getArticleMongoDao().saveOrUpdate(article);
		return article;
	}

	@Override
	public void deleteById(WebContext webCtx) {
		Article article = new Article();
		article.setId(webCtx.getRequestParameter().getString("id"));
		this.getDaoFactory().getArticleMongoDao().remove(article);
	}

	@Override
	public WebResponse findByOrder(Integer order) {
		WebResponse webResponse = new WebResponse();
		Article article = this.getDaoFactory().getArticleMongoDao().findByOrder(order);
		webResponse.put("article", article);
		Article previous = this.getDaoFactory().getArticleMongoDao().findOne(new Query(new Criteria("createDate").gt(article.getCreateDate())).with(new Sort(Sort.Direction.ASC,"createDate")), Article.class);
		Article next = this.getDaoFactory().getArticleMongoDao().findOne(new Query(new Criteria("createDate").lt(article.getCreateDate())).with(new Sort(Sort.Direction.DESC,"createDate")), Article.class);
		webResponse.put("previous", previous);
		webResponse.put("next", next);
		return webResponse;
	}

	@Override
	public Page<Article> pagenation(Class<Article> e, Integer page,
			Integer pageSize, PageCondition condition) {
		return this.getDaoFactory().getArticleMongoDao().pagenation(e, page, pageSize, condition);
	}
	

}
