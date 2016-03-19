package com.techeffic.blog.util;

/**
 * 字符串处理工具类
 * @author k42jc
 *
 */
public class StringUtils {
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
}
