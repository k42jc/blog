/*******************************************************************************
 *    文件名    ： NumberUtil.java
 *             (C) Copyright 金蝶软件 Corporation 2016
 *             All Rights Reserved.
 * *****************************************************************************
 *    注意： 本内容仅限于金蝶软件公司内部使用，禁止转发
 ******************************************************************************/
package com.techeffic.blog.common.util;


/**
 * 数字工具类
 * @author xudong_liao
 * @time 2016年5月4日上午11:10:05
 */
public class NumberUtil {
	
	/**
	 * 浮点型数据四舍五入保留nums位小数
	 * @author xudong_liao
	 * @Time 2016年5月4日上午11:20:19
	 * @param number 待格式化数据
	 * @param nums 保留几位小数点
	 * @return 转换完成后的字符串形式数据 如1.20
	 */
	public static String format(double number,int nums){
//		return new BigDecimal(number).setScale(nums, BigDecimal.ROUND_HALF_UP).doubleValue();
//		return Double.valueOf(new DecimalFormat("#.00").format(number));
		return String.format("%."+nums+"f", number);
	}
	
	/*public static double format(double number,int nums){
		return Double.valueOf(String.format("%."+nums+"f", number));
	}*/
	
	public static void main(String[] args) {
		System.out.println(format(5.19631, 2));
		System.out.println(String.format("%.2f", 5.19631));
	}
}
