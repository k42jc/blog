package com.techeffic.blog.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 微信工具类
 * @author xudong_liao<br/>
 * @date 2016年1月5日<br/>
 */
@Component
public class WeChatUtil {
	@Value("#{settings['weChatToken']}")
	private   String TOKEN = "";
	@Value("#{settings['appId']}")
	private  String APP_ID = "";
	@Value("#{settings['appSecret']}")
	private  String APP_SECRET = "";
	@Value("#{settings['access_token_URL']}")
	private  String ACCESS_TOKEN_URL = "";
	
	/**
	 * 获取access_token
	 * 2016年1月5日
	 * @return
	 *
	 */
	public  String getAccess_token(){
		HttpClient httpClient = new HttpClient();
		HttpMethod httpMethod = new GetMethod(ACCESS_TOKEN_URL);
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
