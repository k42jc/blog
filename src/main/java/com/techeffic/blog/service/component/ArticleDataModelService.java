package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.service.BaseService;
@Service
public class ArticleDataModelService extends BaseService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
//		Integer articleId = webCtx.getRequestParameter().getInt("articleId");
		int articleId = webCtx.getRequestAttribute().getInt("articleId");
		Article article = this.getDaoFactory().getArticleMongoDao().findByOrder(articleId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("article", article);
		return resultMap;
	}

}
