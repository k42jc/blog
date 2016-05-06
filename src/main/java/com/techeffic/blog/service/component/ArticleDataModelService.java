package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.SysData;
import com.techeffic.blog.service.BaseService;
@Service
public class ArticleDataModelService extends BaseService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
//		Integer articleId = webCtx.getRequestParameter().getInt("articleId");
		int articleId = webCtx.getRequestAttribute().getInt("articleId");
		Article article = this.getDaoFactory().getArticleMongoDao().findByOrder(articleId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysData sysData = this.getDaoFactory().getSysDataMongoDao().findOne(new Query(new Criteria("key").is(article.getClazz())), SysData.class);
		resultMap.put("article", article);
		resultMap.put("clazzName", sysData.getValue());
		return resultMap;
	}

}
