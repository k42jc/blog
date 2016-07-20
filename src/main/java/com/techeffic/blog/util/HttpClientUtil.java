package com.techeffic.blog.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import jetbrick.util.JSONUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.json.JSONObject;

/**
 * httpClient工具
 * @author k42jc
 *
 */
public class HttpClientUtil {
	
	/**
	 * 最基础的get请求 返回inputStream
	 * @param remoteURL 请求url
	 * @return inputStream
	 */
	public static InputStream doGetStream(String remoteURL){
		HttpClient httpClient = new HttpClient();
		HttpMethod method = new GetMethod(remoteURL);
		method.getParams().setContentCharset("UTF-8");
		try {
			httpClient.executeMethod(method);
			return method.getResponseBodyAsStream();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			method.releaseConnection();
		}
		return null;
	}
	
	/**
	 * 返回byte[]
	 * @param remoteURL 请求url
	 * @return byte[]
	 */
	public static byte[] doGetByteArray(String remoteURL){
		HttpClient httpClient = new HttpClient();
		HttpMethod method = new GetMethod(remoteURL);
		method.getParams().setContentCharset("UTF-8");
		try {
			httpClient.executeMethod(method);
			return method.getResponseBody();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			method.releaseConnection();
		}
		return null;
	}
	
	/**
	 * 返回byte[]
	 * @param remoteURL 请求url
	 * @return String类型
	 */
	public static String doGetString(String remoteURL){
		return EncodingUtil.getString(doGetByteArray(remoteURL), "UTF-8");
	}
	
	/**
	 * 获取map类型返回结果
	 * @param remoteURL 请求url
	 * @return map类型结果集
	 */
	public static Map<String,Object> doGetMap(String remoteURL){
		return JsonUtil.readValue(doGetString(remoteURL),Map.class);
	}
	
}
