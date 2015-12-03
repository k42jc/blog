package pub.lxd.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;

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
		response.put("blogList", this.serviceFactory.blogService.selectBlogList());
		return response;
	}
	
	@RequestMapping("/upload")
	public void upload(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-Type" , "text/html");
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/writeArticle")
	public String writeArticle(){
		return "writeArticle";
	}
	
	@RequestMapping("/blogList")
	public String blogList(){
		return "blogList";
	}
	
	@RequestMapping("/saveArticle")
	public void saveArticle(@RequestParam(value="title",required=false) String title,
			@RequestParam(value="properties",required=false) String properties,
			@RequestParam(value="content2",required=false) String content,
			@RequestParam(value="keywords",required=false) String keywords){
		System.out.println("xxx");
	}
	
}
