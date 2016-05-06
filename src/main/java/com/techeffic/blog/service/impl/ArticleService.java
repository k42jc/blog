package com.techeffic.blog.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
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
		Article article = new Article();
		article.setId(KeyUtil.generate());
		article.setClazz(clazz);
		article.setType(type);
		article.setTitle(title);
		article.setKeywords(label);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			article.setCreateDate(sdf.parse("2016-04-18 20:36:21"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//找到上一篇文章
		Article previous = this.getDaoFactory().getArticleMongoDao().findPrevious();
		article.setOrder(previous == null?1:previous.getOrder()+1);
		article.setContentHtml(html);
		article.setContentMarkdown(markdown);
		article.setContentView(html.substring(0, 200));
		this.getDaoFactory().getArticleMongoDao().saveOrUpdate(article);
		return article;
	}

	@Override
	public void deleteById(WebContext webCtx) {
		Article article = new Article();
		article.setId(webCtx.getRequestParameter().getString("id"));
		this.getDaoFactory().getArticleMongoDao().remove(article);
	}

}
