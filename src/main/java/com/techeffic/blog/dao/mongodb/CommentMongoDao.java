package com.techeffic.blog.dao.mongodb;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.dao.ICommentDao;
import com.techeffic.blog.entity.Comment;

@Repository
public class CommentMongoDao extends BaseMongoDao<Comment> implements ICommentDao{

	@Override
	public void comment(WebContext webCtx, Comment comment) {
		this.saveOrUpdate(comment);
	}
	
	@Override
	public List<Comment> findCommentByArticleId(String articleId) {
		return this.find(new Query(new Criteria("articleId").is(articleId)).with(new Sort(Sort.Direction.DESC,"createdate")), Comment.class);
	}

}
