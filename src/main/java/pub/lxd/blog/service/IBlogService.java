package pub.lxd.blog.service;

import java.util.List;

import pub.lxd.blog.entity.Blog;

/**
 * 博客Service接口
 * 
 * @author k42jc
 *
 */
public interface IBlogService {
	/**
	 * 查找所有博客
	 * @return 博客集合
	 */
	public List<Blog> selectBlogList();
	
	/**
	 * 查询单个对象
	 * @param blog 单条记录
	 * @return
	 */
	public Blog selectOne(Blog blog);
}
