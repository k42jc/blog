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
	
	@Test
	public void threadTest(){
		Thread thread = new Thread(){
			@Override
			public void run() {
				super.run();
				System.out.println(Thread.currentThread().getName());
				System.out.println("thread running 1");
				System.out.println("thread running 2");
				for(int i=3;i<10;i++){
					System.out.println("thread running "+i);
				}
			}
		};
		System.out.println("开始时间："+System.currentTimeMillis());
		System.out.println("running 0");
		System.out.println(Thread.currentThread().getName());
		thread.start();
		System.out.println("结束时间："+System.currentTimeMillis());
	}
}
