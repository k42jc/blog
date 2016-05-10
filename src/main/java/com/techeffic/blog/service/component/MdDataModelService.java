package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.service.BaseService;
/**
 * 写文章/编辑文章 markdown模式
 * @author k42jc
 *
 */
@Service
public class MdDataModelService extends BaseService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String articleId = webCtx.getRequestAttribute().getString("articleId");
		if(null != articleId){
			resultMap.put("editType", "edit");
			Article article = this.getDaoFactory().getArticleMongoDao().findOne(new Query(new Criteria("id").is(articleId)), Article.class);
			resultMap.put("article", article);
		}else{
			resultMap.put("editType", "add");
		}
		return resultMap;
	}

}
