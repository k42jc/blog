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
