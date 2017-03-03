package com.techeffic.blog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SimpleController {
	// 从 application.properties 中读取配置，如取不到默认值为Hello Shanhy
    /*@Value("${application.hell:Hello Shanhy}")
    private String hello = "Hello Shanhy";*/
	@RequestMapping("/")
	@ResponseBody
	public String home(){
		return "hello world!";
	}
	
	@RequestMapping("/index")
	@ResponseBody
	public String index(){
		return "/indexPage";
	}
	
	/*public static void main(String[] args){
		SpringApplication.run(SimpleController.class, args);
	}*/
}
