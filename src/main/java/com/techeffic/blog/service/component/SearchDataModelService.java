package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.util.URLUtil;
@Service
public class SearchDataModelService extends BaseService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		Map<String,Object> result = new HashMap<String, Object>();
		List<Article> resultList = this.getDaoFactory().getArticleMongoDao().findBySearch(URLUtil.decode(webCtx.getRequestAttribute().getString("content")));
		result.put("datas", resultList);
		return result;
	}

}
