package com.techeffic.blog.example.thread._volatile;

/**
 * Created by liaoxudong on 2017/4/25.
 */
public class Thread1 extends Thread {

    private VolatileTest volatileTest;
    public Thread1(VolatileTest volatileTest){
        this.volatileTest = volatileTest;
    }
    @Override
    public void run() {
        volatileTest.add();
    }
}
