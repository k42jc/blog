package pub.lxd.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pub.lxd.blog.service.ServiceFactory;
/**
 * 基础控制器
 * @author k42jc
 *
 */
@ControllerAdvice
public class BaseController {
	@Autowired
	protected ServiceFactory ServiceFactory;
	/**
	 * 用于controller下所有@requestMapping注解下的异常回调处理
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String handlerControllerException(){
		return "redirect:/500.html";
	}
}
