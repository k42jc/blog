package com.techeffic.blog.dal.dao;


import com.techeffic.blog.dal.entity.mongo.Template;

/**
 * 模板dao接口
 * @author k42jc
 *
 */
public interface ITemplateDao extends IBaseDao<Template>{

	Template findTemplateByRequestURI(String requestURI);

}
