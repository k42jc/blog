package com.techeffic.blog.dao;

import java.util.List;

import com.techeffic.blog.entity.SysData;

/**
 * 系统数据持久层接口
 * @author k42jc
 *
 */
public interface ISysDataDao extends IBaseDao<SysData>{
	
	public List<SysData> findByType(String type);
}
