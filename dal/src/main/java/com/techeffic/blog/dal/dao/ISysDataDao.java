package com.techeffic.blog.dal.dao;


import com.techeffic.blog.dal.entity.mongo.SysData;

import java.util.List;

/**
 * 系统数据持久层接口
 * @author k42jc
 *
 */
public interface ISysDataDao extends IBaseDao<SysData>{
	
	public List<SysData> findByType(String type);
	
	/**
	 * 根据key查询value
	 * @param clazz
	 * @return
	 */
	public SysData findByKey(String clazz);
}
