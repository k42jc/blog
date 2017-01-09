package com.techeffic.blog.example.thread;

public class ThreadA extends Thread{
	@Override
	public void run() {
		for(int i=0;i<100;i++){
//			Tools.tl.set("ThreadA "+(i+1));
//			System.out.println("ThreadA get Value = "+Tools.tl.get());
			System.out.println("ThreadA get Value = "+InheritableTools.itl.get());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
