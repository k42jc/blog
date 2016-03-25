package blog.test.service;

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
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.entity.User;

@RunWith(JUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:application.xml"})  
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
		template.setClassName("indexTemplateService");
		template.setIsIndex("1");
		template.setNeedLogin("0");
		
		this.daoFactory.getTemplateMongoDao().saveOrUpdate(template);
	}
}
