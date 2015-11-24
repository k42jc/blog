package pub.lxd.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		return "index";
	}
	
	@RequestMapping("about")
	public String about(ModelMap map){
		map.put("userName", "廖旭东");
		StringBuffer personInfo = new StringBuffer("一个90后苦逼程序猿。<br/>");
		personInfo.append("一直想做一个属于自己的博客，终于有一天还是忍不住，买了个低配服务器，开始动手。<br/>");
		personInfo.append("使用java开发(只会java~),说到java就不得不提框架(提升big专用~)--spring/springMVC、myBatis(现在最流行最风骚的就是它们了~)，页面使用国人开源模板jetxbrick Template(必须不用jsp,各种Low,无形装逼，最为致命~)。<br/>");
		personInfo.append("服务器安装的Linux centOS(比较节省资源，加上之前学校学过，配合度娘搭配个环境还是没问题~),mysql数据库。<br/>");
		personInfo.append("nginx+tomcat负载均衡,nginx负责静态资源，tomcat负责servlet解析。");
		personInfo.append("然后上面说的都没有什么卵用，因为！！！你们看到的页面，都是拿的别人<font color='red' size='2'>现成模板！！！</font>要我做ui/html就跟要亲命似的，就随便度娘了~<br/>顺便感谢下原作者的无私贡献。");
		personInfo.append("但是呢，也不要过度鄙视，毕竟您<font color='red' size='3'>看到的这些文字还都是后端生成的</font>，未完...");
		personInfo.append("<br/><br/><br/>换几行大声告诉你：<font color='red' size='6'>可以给我留言！！！</font>");
		map.put("personInfo", personInfo);
		return "about";
	}
	
	@RequestMapping("blog/{id}")
	public String toBlogDetail(@PathVariable Long id,ModelMap map){
		Blog blog = new Blog();
		blog.setId(id);
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
