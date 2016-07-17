package com.techeffic.blog.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techeffic.blog.dao.mongodb.BaseMongoDao;
/**
 * dao工厂
 * 直接面向实体dao接口
 * 解除具体数据库的实现 
 * 抽象工厂的spring实现
 * @author k42jc
 *
 */
@Component
public class DaoFactory {
	//用户实例化接口
	private IUserDao userMongoDao;
	//模板实例化接口
	private ITemplateDao templateMongoDao;
	//页面组件实例化接口
	private IComponentDao componentMongoDao;
	//基础数据实例化接口
	private ISysDataDao sysDataMongoDao;
	//文章实例化接口
	private IArticleDao articleMongoDao;
	//评论接口
	private ICommentDao commentMongoDao;
	
	public ICommentDao getCommentMongoDao() {
		return commentMongoDao;
	}
	@Autowired
	public void setCommentMongoDao(ICommentDao commentMongoDao) {
		this.commentMongoDao = commentMongoDao;
	}
	public IArticleDao getArticleMongoDao() {
		return articleMongoDao;
	}
	@Autowired
	public void setArticleMongoDao(IArticleDao articleMongoDao) {
		this.articleMongoDao = articleMongoDao;
	}
	public ISysDataDao getSysDataMongoDao() {
		return sysDataMongoDao;
	}
	@Autowired
	public void setSysDataMongoDao(ISysDataDao sysDataMongoDao) {
		this.sysDataMongoDao = sysDataMongoDao;
	}
	public IComponentDao getComponentMongoDao() {
		return componentMongoDao;
	}
	@Autowired
	public void setComponentMongoDao(IComponentDao componentMongoDao) {
		this.componentMongoDao = componentMongoDao;
	}
	public ITemplateDao getTemplateMongoDao() {
		return templateMongoDao;
	}
	@Autowired
	public void setTemplateMongoDao(ITemplateDao templateMongoDao) {
		this.templateMongoDao = templateMongoDao;
	}

	public IUserDao getUserMongoDao() {
		return userMongoDao;
	}
	
	@Autowired
	public void setUserMongoDao(IUserDao userMongoDao) {
		this.userMongoDao = userMongoDao;
	}
	
	
	
	
}
