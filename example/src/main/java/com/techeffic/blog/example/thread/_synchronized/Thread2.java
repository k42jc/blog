package com.techeffic.blog.example.thread._synchronized;

/**
 * 当前线程用于控制示例程序的循环停止标识赋值为false
 * Created by liaoxudong on 2017/4/25.
 */
public class Thread2 extends Thread{

    private SynTest synTest;

    public Thread2(SynTest synTest) {
        this.synTest = synTest;
    }

    @Override
    public void run() {
        super.run();

        synTest.stop();
    }
}
