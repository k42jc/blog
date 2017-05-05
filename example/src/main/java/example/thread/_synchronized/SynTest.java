package example.thread._synchronized;

/**
 * 用于演示synchronized关键字修饰的同步块有volatile的同步功能
 * Created by liaoxudong on 2017/4/25.
 */
public class SynTest {
    private boolean isContinue = true;

    public synchronized void loop(){
            while(isContinue){
        synchronized (this){// 在死循环内部加锁(需要保证多线程调用时是同一个锁)，实现isContinue变量的可见性，与volatile修饰变量特性一致
    //            System.out.println(isContinue);
        }
            }
        System.out.println("停止了");
    }

    public void stop(){
        this.isContinue = false;
    }

    public static void main(String[] args) throws Exception{
        SynTest synTest = new SynTest();
        Thread1 thread1 = new Thread1(synTest);
        thread1.start();
        Thread.sleep(1000);

        Thread2 thread2 = new Thread2(synTest);
        thread2.start();
        System.out.println("发起停止命令");
    }
}
