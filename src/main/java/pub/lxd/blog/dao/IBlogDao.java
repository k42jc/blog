package pub.lxd.blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import pub.lxd.blog.entity.Blog;
/**
 * 博客dao接口
 * @author k42jc
 *
 */
@Repository
public interface IBlogDao extends IBaseDao<Blog> {
	List<Blog> selectTop5ViewedBlog();
}