package com.techeffic.blog.service;

import org.springframework.web.multipart.MultipartFile;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;

public interface IFileUploadService {
	/**
	 * 文件管理
	 * 2015年12月24日
	 * @param webContext
	 * @return 
	 * @throws Exception
	 *
	 */
	String fileManage(WebContext webContext) throws Exception;
	
	/**
	 * 文件上传
	 * 2015年12月24日
	 * @param webCtx
	 *
	 */
	WebResponse upload(WebContext webCtx,MultipartFile file) throws Exception;
}
