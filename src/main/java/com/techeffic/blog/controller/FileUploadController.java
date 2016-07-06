package com.techeffic.blog.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.techeffic.blog.constants.WebResponse;

/**
 * 管理文件上传
 * 
 * @author xudong_liao<br/>
 * @date 2015年12月24日<br/>
 */
@Controller
@RequestMapping("file")
public class FileUploadController extends BaseController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> uploadAction(
			@RequestParam(value = "file") MultipartFile file) throws Exception {
		// 使用此响应格式可以解决IE8上传完成后弹出下载框的问题
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		return new ResponseEntity<Map<String, Object>>(this
				.getServiceFactory().getFileUploadService()
				.upload(webCtx, file), responseHeaders, HttpStatus.OK);
	}

	@RequestMapping("manage")
	@ResponseBody
	public WebResponse manage() throws Exception {

		return this.getServiceFactory().getFileUploadService()
				.fileManage(webCtx);
	}
}
