package com.techeffic.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techeffic.blog.annotation.IsLogined;
import com.techeffic.blog.constants.WebResponse;

/**
 * 文章相关
 * @author k42jc
 *
 */
@Controller
@RequestMapping("article")
public class ArticleController extends BaseController{
	
	@IsLogined
	@RequestMapping("save")
	@ResponseBody
	public WebResponse save(){
		this.getServiceFactory().getArticleService().save(webCtx);
		return webResponse;
	}
}
