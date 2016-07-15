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
	
	/**
	 * 删除
	 * @author xudong_liao
	 * @Time 2016年7月15日下午6:57:19
	 * @param webCtx
	 * @return
	 */
	WebResponse deleteFile(WebContext webCtx);
}
