package pub.lxd.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping("index")
	public String index(ModelMap map){
		map.put("userName", "廖旭东");
		return "index";
	}
}
