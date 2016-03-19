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
	@Autowired
	public IFileUploadService fileUploadService;
	@Autowired
	public IWeChatService weChatService;
}
