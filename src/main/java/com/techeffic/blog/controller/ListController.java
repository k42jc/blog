package com.techeffic.blog.controller;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.Page;
import com.techeffic.blog.entity.PageCondition;
import com.techeffic.blog.entity.SysData;

/**
 * 文章列表控制器 
 * @author k42jc
 *
 */
@Controller
@RequestMapping("/list")
public class ListController extends BaseController{
	@RequestMapping("getArticleList")
	@ResponseBody
	public WebResponse getArticleList(){
		//查询前10条某个类别的文章并按发表日期逆序
		PageCondition condition = new PageCondition();
		condition.setCondition("clazz", "java");
		condition.setSortColumns("createDate");
		condition.setSortWay(Direction.DESC);
		Page<Article> page = this.getServiceFactory().getArticleService().pagenation(Article.class, webCtx.getRequestParameter().getInt("page"), webCtx.getRequestParameter().getInt("pageSize"), condition);
		webResponse.put("page", page);
		/*SysData sysData = this.getServiceFactory().getSysDataService().findByKey(webCtx.getRequestParameter().getString("type"));
		webResponse.put("sysData", sysData);*/
		if(webCtx.getLoginState().isLogin()){
			webResponse.put("canEdit", true);
		}else{
			webResponse.put("canEdit", false);
		}
		return webResponse;
	}
}
