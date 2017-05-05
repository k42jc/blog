package example.thread._volatile;

/**
 * volatile非原子操作示例
 * Created by liaoxudong on 2017/4/25.
 */
public class VolatileTest {
    private volatile int num = 0;

    public /*synchronized*/ void add(){// add方法如果不是同步的 由于num++运算的非原子性 volatile修饰的num变量最好的输出可能会小于10000
        for(int i=0;i<100;i++){
            num++;
        }
        System.out.println(num);
    }

    public static void main(String[] args) {
        VolatileTest volatileTest = new VolatileTest();
        Thread1[] thread1s = new Thread1[100];
        for(int i=0;i<100;i++){
            thread1s[i] = new Thread1(volatileTest);
        }

        for(int i=0;i<100;i++){
            thread1s[i].start();
        }

    }
}
