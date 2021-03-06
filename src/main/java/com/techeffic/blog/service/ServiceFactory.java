package com.techeffic.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * service工厂
 * @author k42jc
 *
 */
@Component
public class ServiceFactory {
	private IFileUploadService fileUploadService;
	private IWeChatService weChatService;
	private ITemplateService templateService;
	private IComponentService componentService;
	private IArticleService articleService;
	private ISysDataService sysDataService;
	private ICommentService commentService;
	private IWechatPayService wechatPayService;
	
	public IWechatPayService getWechatPayService() {
		return wechatPayService;
	}
	@Autowired
	public void setWechatPayService(IWechatPayService wechatPayService) {
		this.wechatPayService = wechatPayService;
	}
	public ICommentService getCommentService() {
		return commentService;
	}
	@Autowired
	public void setCommentService(ICommentService commentService) {
		this.commentService = commentService;
	}
	public ISysDataService getSysDataService() {
		return sysDataService;
	}
	@Autowired
	public void setSysDataService(ISysDataService sysDataService) {
		this.sysDataService = sysDataService;
	}
	public IArticleService getArticleService() {
		return articleService;
	}
	@Autowired
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}
	public IComponentService getComponentService() {
		return componentService;
	}
	@Autowired
	public void setComponentService(IComponentService componentService) {
		this.componentService = componentService;
	}
	public IFileUploadService getFileUploadService() {
		return fileUploadService;
	}
	@Autowired
	public void setFileUploadService(IFileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}
	
	public IWeChatService getWeChatService() {
		return weChatService;
	}
	@Autowired
	public void setWeChatService(IWeChatService weChatService) {
		this.weChatService = weChatService;
	}
	@Autowired
	public void setTemplateService(ITemplateService templateService) {
		this.templateService = templateService;
	}
	
	public ITemplateService getTemplateService() {
		return this.templateService;
	}
}
