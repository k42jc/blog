package com.techeffic.blog.dal.dao.mongo;

import com.techeffic.blog.dal.dao.ICommentDao;
import com.techeffic.blog.dal.entity.mongo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CommentMongoDao extends BaseMongoDao<Comment> implements ICommentDao {

	@Override
	public void comment(Comment comment) {
		this.saveOrUpdate(comment);
	}
	
	@Override
	public List<Comment> findCommentByArticleId(String articleId) {
		return this.find(new Query(new Criteria("articleId").is(articleId)).with(new Sort(Sort.Direction.DESC,"createdate")), Comment.class);
	}

}
