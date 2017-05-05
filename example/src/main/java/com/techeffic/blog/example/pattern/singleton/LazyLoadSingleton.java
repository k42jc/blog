package com.techeffic.blog.example.pattern.singleton;

/**
 * 懒汉模式 延迟加载
 * Created by liaoxudong on 2017/4/26.
 */
public class LazyLoadSingleton {

    private LazyLoadSingleton() {}

    private static LazyLoadSingleton lazyLoadSingleton;

    public static LazyLoadSingleton getInstance(){
        if(lazyLoadSingleton != null){
            return lazyLoadSingleton;
        }else{
            synchronized(LazyLoadSingleton.class){// 在判断是否实例化时加类锁 确保初始化操作只有一次
                if(lazyLoadSingleton == null){
                        lazyLoadSingleton = new LazyLoadSingleton();
                    }
                }
        }
        return lazyLoadSingleton;
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(LazyLoadSingleton.getInstance().hashCode());
            }
        };
        for(int i=0;i<10;i++){
            Thread thread= new Thread(runnable,"thread_"+i);
            thread.start();
        }

    }
}
