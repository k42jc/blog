package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.techeffic.blog.context.ClientType;
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
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(ClientType.CLIENT_TYPE_MOBILE.equals(webCtx.getClientType().toString())){
			Article article = this.getDaoFactory().getArticleMongoDao().findByOrder(articleId);
			SysData sysData = this.getDaoFactory().getSysDataMongoDao().findOne(new Query(new Criteria("key").is(article.getClazz())), SysData.class);
			resultMap.put("article", article);
			resultMap.put("clazzName", sysData.getValue());
			Article previous = this.getDaoFactory().getArticleMongoDao().findOne(new Query(new Criteria("createDate").gt(article.getCreateDate())).with(new Sort(Sort.Direction.ASC,"createDate")), Article.class);
			Article next = this.getDaoFactory().getArticleMongoDao().findOne(new Query(new Criteria("createDate").lt(article.getCreateDate())).with(new Sort(Sort.Direction.DESC,"createDate")), Article.class);
			resultMap.put("previous", previous);
			resultMap.put("next", next);
		}else{
			resultMap.put("articleId", articleId);
		}
		//上一篇 下一篇
		resultMap.put("clientType", webCtx.getClientType().toString());
		return resultMap;
	}

}
