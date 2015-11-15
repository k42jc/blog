package blog.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pub.lxd.blog.entity.Blog;
import pub.lxd.blog.service.ServiceFactory;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:application.xml"})  
public class ServiceTest {
	@Autowired
	private ServiceFactory serviceFactory;
	
	@Test
	public void blogServiceTest(){
		List<Blog> blogList = serviceFactory.blogService.selectBlogList();
		System.out.println(blogList);
	}
}
