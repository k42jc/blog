package com.techeffic.blog.dao.mongodb;

import org.springframework.stereotype.Repository;

import com.techeffic.blog.dao.ITemplateDao;
import com.techeffic.blog.entity.Template;

/**
 * 模板实例化接口实现类
 * @author k42jc
 *
 */
@Repository
public class TemplateMongoDao extends BaseMongoDao<Template> implements ITemplateDao{

}
