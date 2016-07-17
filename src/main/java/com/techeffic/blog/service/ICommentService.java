package com.techeffic.blog.service;

import com.techeffic.blog.context.WebContext;

/**
 * 用户评论接口
 * @author k42jc
 *
 */
public interface ICommentService {
	/**
	 * 发表评论
	 * @param webCtx
	 * @param blogId 文章id
	 * @param pid 父级评论
	 * @param content 内容
	 */
	public void comment(WebContext webCtx,String articleId,String pid,String content);
}
