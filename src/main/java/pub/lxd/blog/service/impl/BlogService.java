package pub.lxd.blog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	
	public Blog selectOne(Blog blog) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Blog> selectTop5ViewedBlog() {
		return this.daoFactory.blogDao.selectTop5ViewedBlog();
	}

}
