package com.techeffic.blog.dao;

import java.util.List;

import com.techeffic.blog.entity.Template;

/**
 * 模板dao接口
 * @author k42jc
 *
 */
public interface ITemplateDao extends IBaseDao<Template>{

	Template findTemplateByRequestURI(String requestURI);

}
