package com.techeffic.blog.service;

import java.util.Map;

/**
 * 链接提交到百度接口
 * @author k42jc
 *
 */
public interface ISitePushService {
	public Map<String, Object> scheduleSitePush();
	
	/**
	 * 提交单条链接到baidu
	 * @param url
	 * @return
	 */
	public Map<String,Object> push(String url);
}
