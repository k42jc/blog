package com.techeffic.blog.dao;

import java.util.List;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Comment;

/**
 * 用户评论dao层接口
 * @author k42jc
 *
 */
public interface ICommentDao {
	
	/**
	 * 发表评论
	 * @param webCtx
	 * @param blogId 文章id
	 * @param pid 父级评论
	 * @param content 内容
	 */
	public void comment(WebContext webCtx, Comment comment);
	
	/**
	 * 根据文章标识查询评论信息
	 * @param articleId
	 * @return
	 */
	public List<Comment> findCommentByArticleId(String articleId);
}
