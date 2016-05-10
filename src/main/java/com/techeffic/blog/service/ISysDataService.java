package com.techeffic.blog.service;

import com.techeffic.blog.entity.SysData;

/**
 * 系统参数接口
 * @author k42jc
 *
 */
public interface ISysDataService {
	public SysData findByKey(String key);
}
