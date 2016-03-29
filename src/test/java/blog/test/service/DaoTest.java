package blog.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jetbrick.util.codec.MD5Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import blog.test.JUnit4ClassRunner;

import com.techeffic.blog.dao.DaoFactory;
import com.techeffic.blog.entity.Component;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.entity.User;

@RunWith(JUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:application-test.xml"})  
public class DaoTest {
	@Autowired
	public DaoFactory daoFactory;
	
	@Test
	public void testDaoFatory(){
		/*User u =new User();
		u.setId(5L);
		u.setUserName("admin2");
		u.setPassword(MD5Utils.md5Hex("a635684783"));
		System.out.println(this.daoFactory.getUserMongoDao().findById(User.class, "NumberLong(1)"));
		this.daoFactory.getUserMongoDao().saveOrUpdate(u);
		List<User> resultUser = this.daoFactory.getUserMongoDao().findAll(User.class);
		Assert.notNull(resultUser);
		Assert.notEmpty(resultUser);
		
		System.out.println(resultUser.get(0).getId()+"-"+resultUser.get(0).getUserName());*/
		
		Template template = new Template();
		template.setId(UUID.randomUUID().toString());
		template.setRequestURI("/index");
		template.setPath("/index.html");
		template.setNeedLogin("0");
		template.setTitle("首页-技术之路-廖旭东个人博客");
		template.setKeyWords("技术博客，个人博客，技术文章");
		template.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template2 = new Template();
		template2.setId(UUID.randomUUID().toString());
		template2.setRequestURI("/about");
		template2.setPath("/about.html");
		template2.setNeedLogin("0");
		template2.setTitle("个人简介-技术之路-廖旭东个人博客");
		template2.setKeyWords("廖旭东个人简介，技术博客，个人博客，技术文章");
		template2.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template3 = new Template();
		template3.setId(UUID.randomUUID().toString());
		template3.setRequestURI("/login");
		template3.setPath("/login.html");
		template3.setNeedLogin("0");
		template3.setTitle("登录-技术之路-廖旭东个人博客");
		template3.setKeyWords("登录页面，技术博客，个人博客，技术文章");
		template3.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		List<Template> list = new ArrayList<Template>();
		list.add(template);
		list.add(template2);
		list.add(template3);
		
		list.forEach(t -> {
			this.daoFactory.getTemplateMongoDao().saveOrUpdate(t);
		});
	}
	//初始化基础数据
	@Test
	public void initMetaData(){
		Component component = new Component();
		component.setId(UUID.randomUUID().toString());
		component.setKey("header");
		component.setPath("/component/header.html");
		component.setClassName("headerDataModelService");
		
		Component component2 = new Component();
		component2.setId(UUID.randomUUID().toString());
		component2.setKey("footer");
		component2.setPath("/component/footer.html");
		component2.setClassName("footerDataModelService");
		
		Component component3 = new Component();
		component3.setId(UUID.randomUUID().toString());
		component3.setKey("swiper");
		component3.setPath("/component/index/swiper.html");
		component3.setClassName("swiperDataModelService");
		
		Component component4 = new Component();
		component4.setId(UUID.randomUUID().toString());
		component4.setKey("recommend");
		component4.setPath("/component/index/recommend.html");
		component4.setClassName("recommendDataModelService");
		
//		Component component5 = new Component();
//		component5.setId(UUID.randomUUID().toString());
//		component5.setKey("login");
//		component5.setPath("/component/login.html");
//		component5.setClassName("loginComponentService");
		
		List<Component> list = new ArrayList<Component>();
		list.add(component);
		list.add(component2);
		list.add(component3);
		list.add(component4);
		list.forEach(c -> {
			this.daoFactory.getComponentMongoDao().saveOrUpdate(c);
		});
	}
}
