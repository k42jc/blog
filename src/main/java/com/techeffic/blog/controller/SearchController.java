package com.techeffic.blog.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techeffic.blog.entity.Article;
import com.techeffic.blog.util.TemplateUtil;

@Controller
public class SearchController extends BaseController{
	
	@RequestMapping(value="/search/{content}",method=RequestMethod.GET)
	public void search(@PathVariable String content,ModelMap map) throws IOException{
		//查找标题或者文本内容含有content的文章
		List<Article> resultList = this.getServiceFactory().getArticleService().findBySearch(content);
		map.put("datas", resultList);
		OutputStream outputStream = TemplateUtil.render(webCtx.getResponse().getOutputStream(), "/search.html", map);
		outputStream.flush();
		outputStream.close();
	}
}
