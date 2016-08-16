package com.techeffic.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techeffic.blog.util.Log4jUtil;
import com.techeffic.blog.util.QrCodeUtil;

@Controller
@RequestMapping("wechatPay")
public class WechatPayController extends BaseController{
	@RequestMapping("pay")
	public void pay(HttpServletResponse response){
		Map<String,Object> orderInfo = new HashMap<String,Object>();
		String code_url = "";
		try {
			code_url = this.getServiceFactory().getWechatPayService().pay(webCtx, orderInfo);
		} catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.error("请求支付接口失败！", e);
		}
		Log4jUtil.info("".equals(code_url)?"二维码为空！！！！！！！":"调用支付接口成功，二维码链接为："+code_url);
		//生成二维码图片并直接以流的形式输出到页面
		QrCodeUtil.encodeQrcode(code_url, response);
	}
	
	@RequestMapping("callback")
	public void callback(){
		Log4jUtil.info("微信支付成功回调");
	}
}
