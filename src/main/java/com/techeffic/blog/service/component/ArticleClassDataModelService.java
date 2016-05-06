package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.SysData;
import com.techeffic.blog.service.BaseService;
/**
 * 博客分类数据获取service
 * @author k42jc
 *
 */
@Service
public class ArticleClassDataModelService extends BaseService implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<SysData> resultList = this.getDaoFactory().getSysDataMongoDao().find(new Query(new Criteria("type").is(Constants.SYSDATA_TYPE_BLOG_CLAZZ)), SysData.class);
		resultMap.put("typeList", resultList);
		return resultMap;
	}

}
