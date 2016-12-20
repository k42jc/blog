package com.techeffic.blog.example.thread;

/**
 * 可继承的ThreadLocal
 * 可以让子线程从父线程中取得值
 * @author liaoxudong
 *
 */
public class InheritableThreadLocalTest {
	
	public static void main(String[] args) {
		System.out.println(InheritableTools.itl.get());
		
		
		ThreadA a = new ThreadA();
		a.start();
		
		InheritableTools.itl.set("xxxx");
		
		for(int i = 0;i<20;i++){
			System.out.println(InheritableTools.itl.get());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
