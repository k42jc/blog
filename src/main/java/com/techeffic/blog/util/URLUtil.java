package com.techeffic.blog.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * url请求汉字转码
 * @author k42jc
 *
 */
public class URLUtil {
	
	public static String decode(String encoder){
		try {
			return URLDecoder.decode(encoder,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String encode(String chinesse){
		try {
			return URLEncoder.encode(chinesse, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
