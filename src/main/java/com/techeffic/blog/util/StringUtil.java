package com.techeffic.blog.util;

import java.util.Arrays;

import jetbrick.util.ArrayUtils;

/**
 * 字符串处理工具类
 * @author k42jc
 *
 */
public class StringUtil {
	/**
	 * 清空特殊字符串
	 * @param currentStr 需要处理的字符串
	 * @param regex 正则表达式字符串
	 * @return 处理后的字符串
	 */
	public static String emptySpecialStr(String currentStr,String regex){
		if(currentStr == null || regex == null)
			return null;
		return currentStr.replaceAll(regex, currentStr);
	}
	
	/**
	 * 将字符串数组转为stringBuilder对象 用户页面模板生成的字符数组转为正常格式的Html
	 * @param 字符串数组
	 * 使用System.getProperty("line.separator")获取当前系统的换行符，拼接到后面
	 * 否则会造成所有的字符全在一行的情况
	 * @return
	 */
	public static StringBuilder strArrayToHtml(String[] strs) {
		StringBuilder builder = new StringBuilder();
		for(String str : strs){
			builder.append(str).append(System.getProperty("line.separator"));
		}
		return builder;
	}
}
