package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.Page;
import com.techeffic.blog.entity.PageCondition;
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
		//查询前10条某个类别的文章并按发表日期逆序
		PageCondition condition = new PageCondition();
		condition.setCondition("clazz", clazz);
		condition.setSortColumns("createDate");
		condition.setSortWay(Direction.DESC);
		Page<Article> page = this.getDaoFactory().getArticleMongoDao().pagenation(Article.class, 1, 5, condition);
//		List<Article> articleList = this.getDaoFactory().getArticleMongoDao().find(new Query(new Criteria("clazz").is(clazz)).with(new Sort(Sort.Direction.DESC,"createDate")).limit(10), Article.class);
//		resultMap.put("articleList", articleList);
		resultMap.put("page", page);
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
