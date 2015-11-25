package pub.lxd.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pub.lxd.blog.constants.Constants;
import pub.lxd.blog.entity.Blog;
/**
 * 首页控制器
 * @author k42jc
 *
 */
@Controller
public class IndexController extends BaseController{
	@RequestMapping("index")
	public String index(ModelMap map){
		map.put("userName", "廖旭东");
		map.put("job", "java程序员");
		map.put("release", "一个大帅比");
		map.put("blogList", this.serviceFactory.blogService.selectBlogList());
		//首页点击排行top5
		map.put("viewedBlogTop5", this.serviceFactory.blogService.selectTop5ViewedBlog());
		//首页博客分类
		map.put("blogTypeList", this.serviceFactory.blogService.selectBlogTypeList());
		return "index";
	}
	
	@RequestMapping("about")
	public String about(ModelMap map){
		map.put("userName", "廖旭东");
		map.put("release", "一个大帅比");
		map.put("about", this.serviceFactory.sysDataService.selectSysDatByType(Constants.SYSDATA_TYPE_ABOUT).get(0));
		map.put("personInfo", this.serviceFactory.userService.selectUserDetailById(1L));
		return "about";
	}
	
	@RequestMapping("blog/{id}")
	public String toBlogDetail(@PathVariable Long id,ModelMap map){
		Blog blog = new Blog();
		blog.setId(id);
		//顺便更新查阅人数
		map.put("blog", this.serviceFactory.blogService.selectOne(blog));
		map.put("viewedBlogTop5", this.serviceFactory.blogService.selectTop5ViewedBlog());
		map.put("userName", "廖旭东");
		return "blog";
	}
	
	/*@RequestMapping("blog")
	public String blog(ModelMap map){
		map.put("userName", "廖旭东");
		return "blog";
	}
	
	@RequestMapping("leaveMessage")
	public String leaveMessage(ModelMap map){
		map.put("userName", "廖旭东");
		return "leaveMessage";
	}
	
	@RequestMapping("layout")
	public String layout(ModelMap map) throws UnsupportedEncodingException{
		map.put("url", webCtx.getRequest().getParameter("url"));
		return "layout";
	}*/
}
