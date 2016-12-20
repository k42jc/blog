package com.techeffic.blog.example.thread;

/**
 * ThreadLocal实现每个线程都有自己的共享变量 
 * 主要解决的就是每个线程都绑定自己的值
 * @author liaoxudong
 *
 */
public class ThreadLocalTest {
	public static void main(String[] args) {
		ThreadA a = new ThreadA();
		ThreadB b = new ThreadB();
		a.start();
		b.start();
		
		for(int i=100;i<200;i++){
			Tools.tl.set("Main "+(i+1));
			System.out.println("Main get Value = "+Tools.tl.get());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//虽然线程A、B、Main都向tl对象中set不同的值，但每个线程还是能取出自己的数据
	}
}
