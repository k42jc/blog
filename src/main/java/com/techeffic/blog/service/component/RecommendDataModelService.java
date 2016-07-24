package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.SysData;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.util.Log4jUtil;
@Service
public class RecommendDataModelService extends BaseService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//查询文章分类
		Log4jUtil.debug("********查询文章分类*******");
		List<SysData> blogClazzs = this.getDaoFactory().getSysDataMongoDao().findByType("blogClazz");
		blogClazzs.forEach(clazz ->{
			clazz.putProp("nums", this.getDaoFactory().getArticleMongoDao().findNumsByClazz(clazz.getKey()));
		});
		Log4jUtil.debug("********查询点击排行*******");
		//查询点击排行
		List<Article> topArticles = this.getDaoFactory().getArticleMongoDao().findViewedTopArticles(10);
		Log4jUtil.debug("********查询随机推荐*******");
		//查询随机推荐文章
		List<Article> randomArticles = this.getDaoFactory().getArticleMongoDao().findRandomArticles(5);
		randomArticles.forEach(random -> {
			random.putProp("clazz", this.getDaoFactory().getSysDataMongoDao().findByKey(random.getClazz()).getValue());
		});
		resultMap.put("blogClazzs", blogClazzs);
		resultMap.put("topArticles", topArticles);
		resultMap.put("randomArticles", randomArticles);
		Log4jUtil.debug("********返回数据*******");
		return resultMap;
	}

}
