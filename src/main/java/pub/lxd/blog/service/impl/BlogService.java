package pub.lxd.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pub.lxd.blog.constants.Constants;
import pub.lxd.blog.constants.WebContext;
import pub.lxd.blog.entity.Blog;
import pub.lxd.blog.service.BaseService;
import pub.lxd.blog.service.IBlogService;

import org.apache.log4j.Logger;

@Service
/**事务默认为只读，可以提高效率*/
@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
public class BlogService extends BaseService implements IBlogService{
	Logger logger = Logger.getLogger(BlogService.class);
	public List<Blog> selectBlogList() {
		return this.daoFactory.blogDao.selectAll();
	}
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Blog selectOne(Blog blog) {
		this.daoFactory.blogDao.updateBlogViewdById(blog.getId());
		return this.daoFactory.blogDao.selectBlogDetailById(blog.getId());
	}

	public List<Blog> selectTop5ViewedBlog() {
		return this.daoFactory.blogDao.selectTop5ViewedBlog();
	}

	public List<Map<String,Object>> selectBlogTypeList() {
		return this.daoFactory.blogDao.selectBlogTypeList(Constants.SYSDATA_TYPE_BLOG_TYPE);
	}

	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void saveArticle(WebContext webCtx) {
		Blog blog = new Blog();
		//是否原创
		//webCtx.getLong("blogType")
		logger.info(webCtx.getLong("blogType"));
		logger.info(webCtx.getString("title"));
		logger.info(webCtx.getString("content"));
		logger.info(webCtx.getString("keywords"));
		logger.info(webCtx.getString("contentView"));
		blog.setTypeId(webCtx.getLong("blogType"));
		blog.setTitle(webCtx.getString("title"));
		blog.setCreateDate(new Date());
		blog.setContentView(webCtx.getString("contentView").substring(0,350));
		blog.setContent(webCtx.getString("content"));
		blog.setKeyword(webCtx.getString("keywords"));
		this.daoFactory.blogDao.saveArticle(blog);
	}

}
