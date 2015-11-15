package blog.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pub.lxd.blog.dao.IBlogDao;
import pub.lxd.blog.entity.Blog;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:application.xml"})  
public class DaoTest {
	@Autowired
	private IBlogDao blogDao;
	@Test
	public void test() {
		List<Blog> blogList = blogDao.selectAll();
		System.out.println(blogList);
	}

}
