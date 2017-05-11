package com.techeffic.blog.manager;

import com.techeffic.blog.common.server.jetty.BootModel;
import com.techeffic.blog.common.server.jetty.JettyServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 后台管理系统启动入口
 * Created by liaoxudong on 2017/5/8.
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    private static volatile boolean isRunning = true;


    public static void main(String[] args) {
        //启动后台管理服务
        final JettyServer server = new JettyServer(BootModel.WEB_MANAGER);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                server.destroy();
                isRunning = false;
            }
        });

        try {
            logger.info("后台服务开始启动");
            server.init();
            logger.info("后台服务启动完成，当前系统编码："+System.getProperty("file.encoding"));
        } catch (Exception e) {
            logger.error("后台服务启动失败",e);
            System.exit(1);
        }

        synchronized(App.class){
            while(isRunning){
                try {
                    App.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
