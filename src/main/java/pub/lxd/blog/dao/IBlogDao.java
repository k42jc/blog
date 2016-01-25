package pub.lxd.blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pub.lxd.blog.entity.Blog;
/**
 * 博客dao接口
 * @author k42jc
 *
 */
@Repository
public interface IBlogDao extends IBaseDao<Blog> {
	/**
	 * 查询点击前5
	 * @return
	 */
	List<Blog> selectTop5ViewedBlog();
	
	/**
	 * 查询单条博客详情
	 * @param id
	 * @return
	 */
	Blog selectBlogDetailById(Long id);
	
	/**
	 * 首页博客分类显示
	 * @return
	 */
	List<Map<String, Object>> selectBlogTypeList(String blogType);
	
	/**
	 * 用户阅读数量+1
	 * @param id
	 */
	void updateBlogViewdById(Long id);

	/**
	 * 保存文章
	 * 2015年12月24日
	 * @param blog
	 *
	 */
	void saveArticle(Blog blog);
}