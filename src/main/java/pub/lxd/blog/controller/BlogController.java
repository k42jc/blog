package pub.lxd.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pub.lxd.blog.constants.WebResponse;
/**
 * 博客控制器
 * @author k42jc
 *
 */
@Controller
public class BlogController extends BaseController{
	
	@RequestMapping("/blog/list")
	@ResponseBody
	public WebResponse blogList(ModelMap modelMap){
		WebResponse response = new WebResponse(4);
		response.put("blogList", this.ServiceFactory.blogService.selectBlogList());
		return response;
	}
	
	
}
