package com.techeffic.blog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.constants.WebResponse;

/**
 * 用户登录操作controller
 * @author k42jc
 *
 */
@Controller
public class LoginController extends BaseController{
	
	@RequestMapping("login/validate.action")
	@ResponseBody
	public WebResponse validate(){
		String userName = webCtx.getReqeustAttribute().getString("userName");
		String password = webCtx.getReqeustAttribute().getString("password");
		Map<String,Object> map = new HashMap<String,Object>();
		
		webResponse.setSuccess(Constants.FAIL);
		webResponse.setType("error");
		webResponse.put("errors", map);
		if(null ==userName || "".equals(userName)){
			map.put("userName", "用户名不能为空!");
		}else if(null == password || "".equals(password)){
			map.put("password", "密码不能为空！");
		}else if(!"test".equals(userName)){
			map.put("userName", "用户名错误！");
		}else if(!"123456".equals(password)){
			map.put("password", "密码错误！");
		}else{
			String userId = "abcd";
			webResponse.setSuccess(Constants.SUCCESS);
			webResponse.setType("location");
			webResponse.put("data", webCtx.getReqeustAttribute().getString("redirectURI"));
			webCtx.userLogin(userId);
		}
		return webResponse;
	}
	/**
	 * 用户登录操作
	 * @return
	 */
	@RequestMapping("login.action")
	@ResponseBody
	public WebResponse login(){
		String userName = webCtx.getReqeustAttribute().getString("userName");
		String password = webCtx.getReqeustAttribute().getString("password");
		if("test".equals(userName) && "123456".equals(password)){
			webResponse.setSuccess(Constants.SUCCESS);
		}else{
			webResponse.setSuccess(Constants.FAIL);
		}
		return webResponse;
	}
}
