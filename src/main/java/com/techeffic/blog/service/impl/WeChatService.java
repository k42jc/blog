package com.techeffic.blog.service.impl;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.IWeChatService;

/**
 * 微信公众号
 * @author xudong_liao<br/>
 * @date 2016年1月5日<br/>
 */
@Service
public class WeChatService extends BaseService implements IWeChatService{
	Logger logger = Logger.getLogger(WeChatService.class);
	
	@Value("#{settings['token']}")
	private String token = "";
	@Value("#{settings['appId']}")
	private String appId = "";
	@Value("#{settings['appSecret']}")
	private String appSecret = "";
	@Value("#{settings['access_token_URL']}")
	private String access_token_URL = "";
	@Value("#{settings['expires_in']}")
	private int expires_in=0;
	@Value("#{settings['callback_URL']}")
	private String callback_URL="";
	
	
	/**
	 * 提供验证
	 * @return 原样返回echostr参数内容
	 */
	public String checkSignature(WebContext webCtx) {
		String signature = webCtx.getString("signature");
		logger.info("原signture字符"+signature);
		String timestamp = webCtx.getString("timestamp");
		String nonce = webCtx.getString("nonce");
		
		String[] targets = {token,timestamp,nonce};
		//字典排序
		Arrays.sort(targets);
		StringBuffer sb = new StringBuffer();
		for(String target : targets){
			sb.append(target);
		}
		//sha1加密
		String targetString = DigestUtils.sha1Hex(sb.toString());
		logger.info("转换拼接字符"+targetString);
		
		if(signature.equals(targetString))
			return webCtx.getString("echostr");
		return "";
	}
	
	/**
	 * 获取access_token
	 * 2016年1月5日
	 * @return jsonStr
	 *
	 */
	public String getAccess_token(){
		HttpClient httpClient = new HttpClient();
		HttpMethod httpMethod = new GetMethod(access_token_URL);
		httpMethod.getParams().setContentCharset("UTF-8");
		try {
			httpClient.executeMethod(httpMethod);
			String responseBody = httpMethod.getResponseBodyAsString();
			logger.info(responseBody);
			return responseBody;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCallBackIp(String access_token){
		HttpClient httpClient = new HttpClient();
		HttpMethod httpMethod = new GetMethod(callback_URL+access_token);
		httpMethod.getParams().setContentCharset("UTF-8");
		try {
			httpClient.executeMethod(httpMethod);
			return httpMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
