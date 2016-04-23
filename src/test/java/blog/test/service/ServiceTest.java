package blog.test.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.techeffic.blog.entity.Article;
import com.techeffic.blog.service.ServiceFactory;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:application.xml"})  
public class ServiceTest {
	@Autowired
	private ServiceFactory serviceFactory;
	
}
