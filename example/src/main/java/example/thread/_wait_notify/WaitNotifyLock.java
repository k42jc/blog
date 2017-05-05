package example.thread._wait_notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产/消费者模式(等待/通知)  使用Lock实现
 * Created by liaoxudong on 2017/4/25.
 */
public class WaitNotifyLock {
    private boolean second = false;
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public void first(){
        try {
            lock.lock();
            while(second){
                condition.await();
            }
                System.out.println("★");
            second = true;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void second(){
        try {
            lock.lock();
            while(!second){
                condition.await();
            }
                System.out.println("☆");
            second = false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static class Thread1 extends Thread{
        WaitNotifyLock s;

        public Thread1(WaitNotifyLock s) {
            this.s = s;
        }

        @Override
        public void run() {
            for(int i=0;i<10;i++)
            s.first();
        }
    }

    static class Thread2 extends Thread{
        WaitNotifyLock s;

        public Thread2(WaitNotifyLock s) {
            this.s = s;
        }

        @Override
        public void run() {
            for(int i=0;i<10;i++)
            s.second();
        }
    }

    public static void main(String[] args) {
        WaitNotifyLock waitNotifyLock = new WaitNotifyLock();
//         for(int i=0;i<10;i++){
            Thread1 thread1 = new Thread1(waitNotifyLock);
            Thread2 thread2 = new Thread2(waitNotifyLock);

            thread1.start();
            thread2.start();

//         }


    }
}
