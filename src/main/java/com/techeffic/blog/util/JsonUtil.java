package com.techeffic.blog.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json工具类
 * @author k42jc
 *
 */
public class JsonUtil {
	
	/**
	 * json格式数据转为Map
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> toMap(String jsonStr){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			Iterator ite = jsonObject.keys();
			while(ite.hasNext()){
				Object key = ite.next();
				resultMap.put(key.toString(), jsonObject.get(key.toString()));
			}
			return resultMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
