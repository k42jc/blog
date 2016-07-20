package blog.test.service;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.techeffic.blog.util.HttpClientUtil;

public class NormalTest {
	/*@Test
	public void markdownTest() throws IOException{
		Markdown4jProcessor markdown = new Markdown4jProcessor();
//		System.out.println(System.getProperty("user.dir"));
		String html = new Markdown4jProcessor().process(new File("C:/Users/k42jc/Desktop/New Document.md"));
		System.out.println(html);
	}*/
	
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
	
	
	@Test
	public void httpClientTest(){
		Map<String,Object> addrInfo = HttpClientUtil.doGetMap("http://ip.taobao.com/service/getIpInfo.php?ip=114.215.205.4");
		addrInfo.forEach((k,v)->{
			System.out.println(k+"---"+v);
		});
	}
	
	public static void main(String[] args) {
		try {

			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("admin");
					
			boolean auth = db.authenticate("k42jc", "a635684783".toCharArray());
			if (auth) {
					
				DBCollection table = db.getCollection("sysData");

				/*BasicDBObject document = new BasicDBObject();
				document.put("name", "mkyong");
				table.insert(document);*/
				System.out.println(table.findOne());
			
				System.out.println("Login is successful!");
			} else {
				System.out.println("Login is failed!");
			}
			System.out.println("Done");

		    } catch (UnknownHostException e) {
			e.printStackTrace();
		    } catch (MongoException e) {
			e.printStackTrace();
		    }
	}
}
