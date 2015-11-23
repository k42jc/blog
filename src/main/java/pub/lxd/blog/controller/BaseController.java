package pub.lxd.blog.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import pub.lxd.blog.constants.WebContext;
import pub.lxd.blog.service.ServiceFactory;

import com.baidu.ueditor.ActionEnter;

/**
 * 基础控制器
 * 
 * @author k42jc
 *
 */
@ControllerAdvice
public class BaseController {
	protected WebContext webCtx;
	@Autowired
	protected ServiceFactory serviceFactory;

	/**
	 * 用于controller下所有@requestMapping注解下的异常回调处理
	 * 
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String handlerControllerException() {
		return "redirect:/500.html";
	}
	
	@ModelAttribute
	public void init(HttpServletRequest request,HttpServletResponse response){
		webCtx = new WebContext(request, response);
	}
	
	

}
