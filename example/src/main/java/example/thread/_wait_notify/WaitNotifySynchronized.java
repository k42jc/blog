package example.thread._wait_notify;

/**
 * 生产/消费(等待/唤醒)演示一  ---------  使用synchronized实现
 * 轮流打印★与☆10次
 * Created by liaoxudong on 2017/4/25.
 */
public class WaitNotifySynchronized {
    private boolean second = false;

    synchronized void first() throws InterruptedException {
        while(second){
            wait();
        }
        System.out.println("★");
        second = true;
        notifyAll();
    }

    synchronized void second() throws InterruptedException {
        while(!second){
            wait();
        }
        System.out.println("☆");
        second = false;
        notifyAll();
    }

    public static void main(String[] args) {
        WaitNotifySynchronized waitNotifySynchronized = new WaitNotifySynchronized();
//        for(int i=0;i<10;i++){
            Thread2 thread2 = new Thread2(waitNotifySynchronized);
            Thread1 thread1 = new Thread1(waitNotifySynchronized);
            thread2.start();
            thread1.start();
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

//        }
    }

    static class Thread1 extends Thread{
        private WaitNotifySynchronized waitNotifySynchronized;

        public Thread1(WaitNotifySynchronized waitNotifySynchronized){
            this.waitNotifySynchronized = waitNotifySynchronized;
        }
        @Override
        public void run() {
//        super.run();
            try {
                for(int i=0;i<10;i++)
                waitNotifySynchronized.first();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Thread2 extends Thread{
        private WaitNotifySynchronized waitNotifySynchronized;

        public Thread2(WaitNotifySynchronized waitNotifySynchronized){
            this.waitNotifySynchronized = waitNotifySynchronized;
        }
        @Override
        public void run() {
//        super.run();
            try {
                for(int i=0;i<10;i++)
                waitNotifySynchronized.second();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
