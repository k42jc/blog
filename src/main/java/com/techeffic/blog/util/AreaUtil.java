package com.techeffic.blog.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 地区工具类 根据淘宝ip地址库获取地区数据
 * @author k42jc
 *
 */
public class AreaUtil {
	/**
	 * 获得整体数据信息  
	 * {"area":"华东","country":"中国","isp_id":"1000323","city":"杭州市","isp":"阿里云","ip":"114.215.205.4","region_id":"330000","county":"","area_id":"300000","county_id":"-1","region":"浙江省","country_id":"CN","city_id":"330100"}
	 * @param remoteUrl 请求url：http://ip.taobao.com/service/getIpInfo.php?ip=114.215.5.4
	 * @return
	 */
	public static Map<String,Object> getArea(String remoteUrl){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> areaInfo = HttpClientUtil.doGetMap(remoteUrl);
		if("0".equals(areaInfo.get("code").toString())){
			resultMap = (Map<String,Object>)areaInfo.get("data");
		}
		return resultMap;
	}
}
