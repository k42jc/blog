package com.techeffic.blog.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 微信公众号接入控制器
 * @author k42jc
 *
 */
@Controller
@RequestMapping("weChat")
public class WeChatController extends BaseController{
	Logger logger = Logger.getLogger(WeChatController.class);
	@RequestMapping("cut")
	@ResponseBody
	public String weChatCut(){
		/*map.put("userName", "廖旭东");
		map.put("job", "java程序员");
		map.put("release", "一个大帅比");
		map.put("blogList", this.serviceFactory.blogService.selectBlogList());
		//首页点击排行top5
		map.put("viewedBlogTop5", this.serviceFactory.blogService.selectTop5ViewedBlog());
		//首页博客分类
		map.put("blogTypeList", this.serviceFactory.blogService.selectBlogTypeList());*/
		String result = this.getServiceFactory().getWeChatService().checkSignature(webCtx);
		logger.info("结果字符串为："+result);
		if(result == null || "".equals(result)){
			return "结果是空的哟";
		}
		return result;
	}
	
	
}
