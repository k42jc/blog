package pub.lxd.blog.service;

import pub.lxd.blog.constants.WebContext;
import pub.lxd.blog.constants.WebResponse;

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
	String fileUpload(WebContext webCtx) throws Exception;
}
