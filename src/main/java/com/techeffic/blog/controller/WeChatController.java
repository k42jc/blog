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
@RequestMapping("wechat")
public class WeChatController extends BaseController{
	Logger logger = Logger.getLogger(WeChatController.class);
	@RequestMapping("auth")
	@ResponseBody
	public String auth(){
		String result = this.getServiceFactory().getWeChatService().checkSignature(webCtx);
		logger.info("结果字符串为："+result);
		return result;
	}
	
	
}
