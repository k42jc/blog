package com.techeffic.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techeffic.blog.annotation.IsLogined;
import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.SysData;
import com.techeffic.blog.util.CalendarUtil;
import com.techeffic.blog.util.DateUtil;

/**
 * 文章相关
 * 
 * @author k42jc
 *
 */
@Controller
@RequestMapping("article")
public class ArticleController extends BaseController {

	@IsLogined
	@RequestMapping("save")
	@ResponseBody
	public WebResponse save() throws ServletException, IOException {
		Article article = this.getServiceFactory().getArticleService()
				.save(webCtx);
		// webCtx.getResponse().sendRedirect("/article/"+article.getOrder());
		webResponse.put("articleId", article.getId());
		return webResponse;
		// webCtx.getRequest().getRequestDispatcher("/article/"+article.getOrder()).forward(webCtx.getRequest(),
		// webCtx.getResponse());
	}

	@IsLogined
	@RequestMapping("delete")
	@ResponseBody
	public WebResponse delete() {
		this.getServiceFactory().getArticleService().deleteById(webCtx);
		return webResponse;
	}
	
	@RequestMapping("getArticleByOrder")
	@ResponseBody
	public WebResponse getArticleByOrder(){
		webResponse = this.getServiceFactory().getArticleService().findById(webCtx.getRequestParameter().getString("articleId"));
		SysData sysData = this.getServiceFactory().getSysDataService().findByKey(((Article)webResponse.get("article")).getClazz());
		webResponse.put("clazzName", sysData.getValue());
		webResponse.put("createTime", DateUtil.toDateStr(((Article)webResponse.get("article")).getCreateDate()));
		return webResponse;
	}
}
