package example.thread._synchronized;

/**
 * 当前线程用于示例程序的进入死循环操作
 * Created by liaoxudong on 2017/4/25.
 */
public class Thread1 extends Thread {
    private SynTest synTest;

    public Thread1(SynTest synTest) {
        this.synTest = synTest;
    }

    @Override
    public void run() {
        super.run();

        synTest.loop();
    }
}
