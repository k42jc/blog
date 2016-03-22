package blog.test;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * 自定义JUnit的Runner方法 
 * 继承自SpringJUnit4ClassRunner 用于自定义log4j配置文件位置
 * @author k42jc
 *
 */
public class JUnit4ClassRunner extends SpringJUnit4ClassRunner{
	private static final Logger logger = Logger.getLogger(JUnit4ClassRunner.class); 
	
	static{
		try {
			Log4jConfigurer.initLogging("classpath:properties/log4j.properties");
		} catch (FileNotFoundException e) {
			logger.error("加载log4j配置文件失败！");
		}
	}

	public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

}
