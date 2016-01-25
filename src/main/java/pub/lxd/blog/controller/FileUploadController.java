package pub.lxd.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 管理文件上传
 * @author xudong_liao<br/>
 * @date 2015年12月24日<br/>
 */
@Controller
@RequestMapping("file")
public class FileUploadController extends BaseController{
	
	@RequestMapping("upload")
	@ResponseBody
	public String upload() throws Exception{
		return this.serviceFactory.fileUploadService.fileUpload(webCtx);
	}
	
	@RequestMapping("manage")
	@ResponseBody
	public String manage() throws Exception{
		
		return this.serviceFactory.fileUploadService.fileManage(webCtx);
	}
}
