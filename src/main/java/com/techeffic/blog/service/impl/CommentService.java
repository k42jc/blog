package com.techeffic.blog.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Comment;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.ICommentService;
import com.techeffic.blog.util.AreaUtil;
import com.techeffic.blog.util.HttpClientUtil;
import com.techeffic.blog.util.KeyUtil;
import com.techeffic.blog.util.Log4jUtil;
@Service
public class CommentService extends BaseService implements ICommentService{
	@Value("#{settings['taobaoIPUrl']}")
	private String taobaoIPUrl = "";
	@Override
	public void comment(WebContext webCtx, String articleId, String pid,
			String content) {
		Comment comment = new Comment();
		String ip;
		try {
			ip = webCtx.getIP();
			Map<String,Object> areaMap = AreaUtil.getArea(taobaoIPUrl+ip);
			comment.setUserName(areaMap.get("region").toString()+areaMap.get("city").toString()+"-"+ip);
		} catch (Exception e) {
			comment.setUserName("游客");
		}
		comment.setId(KeyUtil.generate());
		comment.setArticleId(articleId);
		comment.setContent(content);
		comment.setCreatedate(new Date());
		comment.setPid(pid);
		this.getDaoFactory().getCommentMongoDao().comment(webCtx, comment);
	}

}
