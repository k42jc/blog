package com.techeffic.blog.dal.dao.mongo;

import com.techeffic.blog.dal.dao.ITemplateDao;
import com.techeffic.blog.dal.entity.mongo.Template;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


/**
 * 模板实例化接口实现类
 * @author k42jc
 *
 */
@Repository
public class TemplateMongoDao extends BaseMongoDao<Template> implements ITemplateDao {

	@Override
	public Template findTemplateByRequestURI(String requestURI) {
		return this.getMongoTemplate().findOne(new Query(new Criteria("requestURI").is(requestURI)), Template.class);
	}

}
