package pub.lxd.blog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pub.lxd.blog.constants.Constants;
import pub.lxd.blog.entity.Blog;
import pub.lxd.blog.service.BaseService;
import pub.lxd.blog.service.IBlogService;

@Service
/**事务默认为只读，可以提高效率*/
@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
public class BlogService extends BaseService implements IBlogService{
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

}
