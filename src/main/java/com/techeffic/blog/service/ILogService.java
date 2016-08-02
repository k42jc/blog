package com.techeffic.blog.service;

import com.techeffic.blog.entity.Log;

/**
 * 操作日志service接口
 * @author k42jc
 *
 */
public interface ILogService {
	
	/**
	 * 记录日志
	 * @param log
	 */
	public void log(Log log);
}
