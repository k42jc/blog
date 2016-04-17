package blog.test.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.markdown4j.Markdown4jProcessor;

public class NormalTest {
	@Test
	public void markdownTest() throws IOException{
		Markdown4jProcessor markdown = new Markdown4jProcessor();
//		System.out.println(System.getProperty("user.dir"));
		String html = new Markdown4jProcessor().process(new File("C:/Users/k42jc/Desktop/New Document.md"));
		System.out.println(html);
	}
	
	@Test
	public void mapTest(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "test");
		map.put("password", "123456");
		map.forEach((key,value) -> {
			System.out.println(key+"-"+value);
		});
	}
}
