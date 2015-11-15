package pub.lxd.blog.dao;

import org.springframework.stereotype.Repository;

import pub.lxd.blog.entity.Comment;
@Repository
public interface ICommentDao extends IBaseDao<Comment> {
	
}