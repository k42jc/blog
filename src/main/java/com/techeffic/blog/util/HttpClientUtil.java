package com.techeffic.blog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * httpClient工具
 * 
 * @author k42jc
 *
 */
public class HttpClientUtil {

	/**
	 * 最基础的get请求 返回inputStream
	 * 
	 * @param remoteURL
	 *            请求url
	 * @return inputStream
	 */
	public static InputStream doGetStream(String remoteURL) {
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
		} finally {
			method.releaseConnection();
		}
		return null;
	}

	/**
	 * 返回byte[]
	 * 
	 * @param remoteURL
	 *            请求url
	 * @return byte[]
	 */
	public static byte[] doGetByteArray(String remoteURL) {
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
		} finally {
			method.releaseConnection();
		}
		return null;
	}

	/**
	 * 返回byte[]
	 * 
	 * @param remoteURL
	 *            请求url
	 * @return String类型
	 */
	public static String doGetString(String remoteURL) {
		return EncodingUtil.getString(doGetByteArray(remoteURL), "UTF-8");
	}

	/**
	 * 获取map类型返回结果
	 * 
	 * @param remoteURL
	 *            请求url
	 * @return map类型结果集
	 */
	public static Map<String, Object> doGetMap(String remoteURL) {
		return JsonUtil.readValue(doGetString(remoteURL), Map.class);
	}

	/**
	 * 返回Byte数组格式数据
	 * 
	 * @param remoteURL
	 * @return
	 */
	public static byte[] doPostByte(String remoteURL, Map<String, Object> params) {
		HttpClient client = new HttpClient();
		HttpMethod postMethod = new PostMethod(remoteURL);
		HttpMethodParams methodParams = new HttpMethodParams();
		params.forEach((k, v) -> {
			methodParams.setParameter(k, v);
		});
		postMethod.setParams(methodParams);
		postMethod.getParams().setContentCharset("UTF-8");
		try {
			client.executeMethod(postMethod);
			return postMethod.getResponseBody();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回字符串格式数据
	 * 
	 * @param remoteURL
	 *            请求url
	 * @return String类型
	 */
	public static String doPostString(String remoteURL,
			Map<String, Object> params) {
		return EncodingUtil.getString(doPostByte(remoteURL, params), "UTF-8");
	}

	/**
	 * 获取map类型返回结果
	 * 
	 * @param remoteURL
	 *            请求url
	 * @return map类型结果集
	 */
	public static Map<String, Object> doPostMap(String remoteURL,
			Map<String, Object> params) {
		return JsonUtil.readValue(doPostString(remoteURL, params), Map.class);
	}
	
	
	/**
	 * POST方式提交链接 用户微信支付提交url到weixin
	 * @param urlStr 目标url
	 * @param data 字符串格式的xml数据
	 * @return
	 */
	public static String postData(String urlStr, String data) {
		return postData(urlStr, data, null);
	}

	public static String postData(String urlStr, String data, String contentType) {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			if (StringUtils.isNotBlank(contentType))
				conn.setRequestProperty("content-type", contentType);
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			if (data == null)
				data = "";
			writer.write(data);
			writer.flush();
			writer.close();

			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();
		} catch (IOException e) {
			Log4jUtil.error("Error connecting to " + urlStr + ": "
					+ e.getMessage());
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

}
