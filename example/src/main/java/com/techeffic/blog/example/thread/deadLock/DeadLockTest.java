package com.techeffic.blog.example.thread.deadLock;

/**
 * 多线程死锁演示 使用锁嵌套的方式演示
 * Created by liaoxudong on 2017/4/25.
 */
public class DeadLockTest implements Runnable{
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private int param;

    public void setParam(int param) {
        this.param = param;
    }

    public void run(){
        if(1 == param){
            synchronized (lock1){
                System.out.println("锁1运行");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println("锁1中获取锁2");
                }
            }
        }else{
            synchronized (lock2){
                System.out.println("锁2运行");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1){
                    System.out.println("锁2中获取锁1");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLockTest deadLockTest = new DeadLockTest();
        deadLockTest.setParam(1);
        Thread thread1 = new Thread(deadLockTest);
        thread1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deadLockTest.setParam(2);
        Thread thread2 = new Thread(deadLockTest);
        thread2.start();

    }


}
