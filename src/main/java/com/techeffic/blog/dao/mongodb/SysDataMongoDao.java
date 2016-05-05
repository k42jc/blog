package com.techeffic.blog.dao.mongodb;

import org.springframework.stereotype.Repository;

import com.techeffic.blog.dao.ISysDataDao;
import com.techeffic.blog.entity.SysData;

/**
 * 基础数据持久层实现
 * @author k42jc
 *
 */
@Repository
public class SysDataMongoDao extends BaseMongoDao<SysData> implements ISysDataDao{

}
