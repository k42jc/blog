package com.techeffic.blog.context;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 重写httpServletRequest
 * @author xudong_liao<br/>
 * @date 2016年4月21日<br/>
 */
public final class Request extends HttpServletRequestWrapper{

	public Request(HttpServletRequest request) {
		super(request);
	}
	/**
	 * 获取当前请求的完整URL
	 * 2016年4月21日
	 * @return
	 *
	 */
	public String getFullRequestURL(){
		return getRequestURL() + getParameterStr();
	}
	
	/**
	 * 获取当前请求的headers
	 * 2016年4月21日
	 * @return
	 *
	 */
	public Map<String,Object> getHeaders(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
//		StringBuffer buffer = new StringBuffer();
		Enumeration<String> headerNames = getHeaderNames();
		while(headerNames.hasMoreElements()){
			String headerName = headerNames.nextElement();
			/*buffer.append(headerName+"\n");*/
			resultMap.put(headerName, getHeader(headerName));
		}
//		return buffer.toString();
		return resultMap;
	}
	
	/**
	 * 将请求参数集转换为"?key=value&key=value"形式
	 * 2016年4月21日
	 * @param requestMap
	 * @return
	 *
	 */
	public String getParameterStr(){
		Map<String,Object> requestMap = getParameterObjectMap();
		if(requestMap.isEmpty()){
			return "";
		}
		StringBuffer buffer = new StringBuffer("?");
		requestMap.forEach((key,value) -> {
			buffer.append(key+"="+value+"&");
		});
		return buffer.deleteCharAt(buffer.lastIndexOf("&")).toString();
	}
	
	/**
	 * 获取当前请求的请求参数
	 * 2016年4月21日
	 * @param clazz
	 * @return
	 *
	 */
	public Map<String,Object> getParameterObjectMap(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Enumeration<String> parameterNames = getParameterNames();
		while(parameterNames.hasMoreElements()){
			String parameterName = parameterNames.nextElement();
			resultMap.put(parameterName, getParameter(parameterName));
		}
		return resultMap;
		/*Map<String,String[]> parameterMap = getParameterMap();
		if(parameterMap == null)
			return null;
		parameterMap.forEach((key,value)->{
			if(value.length == 0){
				resultMap.put(key, (T) value[0]);
			}else{
				StringBuffer buffer = new StringBuffer();
				for(int i=0;i<value.length;i++){
					if(i == 0){
						buffer.append("[");
					}
					buffer.append(value[i]);
					if(i != value.length-1){
						buffer.append(",");
					}else{
						buffer.append("]");
					}
				}
				resultMap.put(key, (T)buffer.toString());
			}
			
		});
		return resultMap;*/
	}
	
}
