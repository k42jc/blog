package com.techeffic.blog.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符类操作工具类
 * Created by liaoxudong on 2017/4/18.
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param val 待判断字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String val){
        return (null == val || val.trim().length() == 0)?true:false;
    }

    /**
     * 字符串转换为指定对象类型
     * @param str 字符串
     * @param clazz 指定类型
     * @param <T> 泛型
     * @return 转换后的值
     */
    public static <T> T strToObj(String str,Class<T> clazz){
        if (clazz.equals(Byte.class)) {
            return (T) Byte.valueOf(str.trim());
        } else if (clazz.equals(Short.class)) {
            return (T) Short.valueOf(str.trim());
        } else if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(str.trim());
        } else if (clazz.equals(Long.class)) {
            return (T) Long.valueOf(str.trim());
        } else if (clazz.equals(Float.class)) {
            return (T) Float.valueOf(str.trim());
        } else if (clazz.equals(Double.class)) {
            return (T) Double.valueOf(str.trim());
        } else if (clazz.equals(Character.class)) {
            return (T) Character.valueOf(str.trim().toCharArray()[0]);
        } else if (clazz.equals(String.class)) {
            return (T) str.trim();
        }
        return null;
    }

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

    /**
     *  源字符串是否包含特定字符串
     * @param source	源字符串
     * @param key			需要搜索的特定字符串
     * @return
     */
    public static boolean contains(String source,String key){
        boolean result = false;
        if(StringUtil.isNotNull(source)){
            if(StringUtil.isNull(key)){
                result = true;
            }else{
                result = (source.indexOf(key) >= 0);
            }
        }
        return result;
    }

    /**
     * 是否为空 <br/>
     * RD_haitao_ou<br/>
     * 下午4:15:59<br/>
     * @param o
     * @return<br/>
     */
    public static boolean isNull(String o) {
        return o == null || o.trim().equals("");
    }

    /**
     *  判断字符串是否非空
     */
    public static boolean isNotNull(String o){
        return o != null && o.trim().length() > 0;
    }

    /**
     * 判断是否是email <br/>
     * RD_haitao_ou<br/>
     * 下午4:16:15<br/>
     *
     * @param email
     * @return<br/>
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email))
            return false;
        // Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否是手机号码 <br/>
     * RD_haitao_ou<br/>
     * 下午4:19:10<br/>
     *
     * @param mobile
     * @return<br/>
     */
    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * StringJoin <br/>
     * RD_haitao_ou<br/>
     * 下午4:52:13<br/>
     *
     * @param join
     * @param strList
     * @return<br/>
     */
    public static String join(String join, List<String> strList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strList.size(); i++) {
            if (i == (strList.size() - 1)) {
                sb.append(strList.get(i));
            } else {
                sb.append(strList.get(i)).append(join);
            }
        }
        return new String(sb);
    }

    /**
     * 生成随机数
     * @author xudong_liao
     * @Time 2016年4月28日下午5:05:21
     * @param size
     * @return
     */
    public static String randomStr(int size){
        return RandomStringUtils.randomNumeric(size);
    }

    /**
     * 是否是ip <br/>
     * RD_haitao_ou<br/>
     * 上午11:11:26<br/>
     *
     * @param addr
     * @return<br/>
     */
    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        return ipAddress;
    }
}
