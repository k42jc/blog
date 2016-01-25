package pub.lxd.blog.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pub.lxd.blog.constants.Constants;
import pub.lxd.blog.constants.WebResponse;
/**
 * 博客控制器
 * @author k42jc
 *
 */
@Controller
public class BlogController extends BaseController{
	
	/*@RequestMapping("/blog/list")
	@ResponseBody
	public WebResponse blogList(ModelMap modelMap){
		WebResponse response = new WebResponse(4);
		response.put("blogList", this.serviceFactory.blogService.selectBlogList());
		return response;
	}*/
	
	
	@RequestMapping("/writeArticle")
	public String writeArticle(ModelMap map){
		map.put("blogTypeList",this.serviceFactory.sysDataService.selectSysDatByType(Constants.SYSDATA_TYPE_BLOG_TYPE));
		return "writeArticle";
	}
	
	@RequestMapping("/blogList")
	public String blogList(ModelMap map){
		map.put("userName","廖旭东");
		map.put("blogList", this.serviceFactory.blogService.selectBlogList());
		return "blogList";
	}
	
	@RequestMapping("/saveArticle")
	@ResponseBody
	public WebResponse saveArticle(){
		/*Map<String,String[]> map = webCtx.getRequest().getParameterMap();
		Set<String> keySet = map.keySet();
		Iterator<String> keys = keySet.iterator();
		while(keys.hasNext()){
			String key = keys.next();
			System.out.println(key+"--"+map.get(key)[0]);
		}
		System.out.println(map);*/
		this.serviceFactory.blogService.saveArticle(webCtx);
		WebResponse response = new WebResponse();
		return response;
		//System.out.println("xxx");
	}
	
}
