package com.techeffic.blog.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techeffic.blog.annotation.IsLogined;
import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.util.TemplateUtil;

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
	public void save() throws ServletException, IOException{
		Article article = this.getServiceFactory().getArticleService().save(webCtx);
		webCtx.getResponse().sendRedirect("/article/"+article.getOrder());
//		webCtx.getRequest().getRequestDispatcher("/article/"+article.getOrder()).forward(webCtx.getRequest(), webCtx.getResponse());
	}
	
	@IsLogined
	@RequestMapping("delete")
	@ResponseBody
	public WebResponse delete(){
		this.getServiceFactory().getArticleService().deleteById(webCtx);
		return webResponse;
	}
}
