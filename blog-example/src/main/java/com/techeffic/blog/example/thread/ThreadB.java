package com.techeffic.blog.example.thread;

public class ThreadB extends Thread{
	@Override
	public void run() {
		for(int i=100;i>0;--i){
			Tools.tl.set("ThreadB "+(i+1));
			System.out.println("ThreadB get Value = "+Tools.tl.get());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
