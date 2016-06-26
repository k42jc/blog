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
		//查找文章分类参数
		List<SysData> blogTypes = this.getDaoFactory().getSysDataMongoDao().findByType("blogType");
		List<SysData> blogClazzs = this.getDaoFactory().getSysDataMongoDao().findByType("blogClazz");
		
		resultMap.put("type", createHtml(blogTypes));
		resultMap.put("clazz", createHtml(blogClazzs));
		return resultMap;
	}

	private String createHtml(List<SysData> sysDatas) {
		StringBuilder html = new StringBuilder();
		for(SysData sysData:sysDatas){
			html.append("<option value=\"").append(sysData.getKey()).append("\">").append(sysData.getValue()).append("</option>");
		}
		return html.toString();
	}

}
