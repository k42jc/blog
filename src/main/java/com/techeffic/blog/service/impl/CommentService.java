package com.techeffic.blog.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Comment;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.ICommentService;
import com.techeffic.blog.util.KeyUtil;
@Service
public class CommentService extends BaseService implements ICommentService{

	@Override
	public void comment(WebContext webCtx, String articleId, String pid,
			String content) {
		Comment comment = new Comment();
		comment.setId(KeyUtil.generate());
		comment.setArticleId(articleId);
		comment.setContent(content);
		comment.setCreatedate(new Date());
		comment.setPid(pid);
		this.getDaoFactory().getCommentMongoDao().comment(webCtx, comment);
	}

}
