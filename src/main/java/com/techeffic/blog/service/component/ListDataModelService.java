package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.SysData;
import com.techeffic.blog.service.BaseService;
/**
 * 博客列表数据获取service
 * @author k42jc
 *
 */
@Service
public class ListDataModelService extends BaseService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String clazz = webCtx.getRequestAttribute().getString("articleClazz");
		List<Article> articleList = this.getDaoFactory().getArticleMongoDao().find(new Query(new Criteria("clazz").is(clazz)), Article.class);
		resultMap.put("articleList", articleList);
		SysData sysData = this.getDaoFactory().getSysDataMongoDao().findOne(new Query(new Criteria("key").is(clazz)), SysData.class);
		resultMap.put("sysData", sysData);
		if(webCtx.getLoginState().isLogin()){
			resultMap.put("canEdit", true);
		}else{
			resultMap.put("canEdit", false);
		}
		return resultMap;
	}

}
