package com.techeffic.blog.example.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * 简单文件服务器示例
 * Created by liaoxudong on 2017/5/15.
 */
public class SimpleFileServer {

    public static void main(String[] args) {
        // 创建端口为8080的jetty server
        Server server = new Server(8080);

        ResourceHandler resourceHandler = new ResourceHandler();
        //配置对外暴露的文件位置，此处设置为当前类 target/classes/ 路径
        resourceHandler.setDirectoriesListed(true);
//        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
        resourceHandler.setResourceBase(path);

        server.setHandler(resourceHandler);


        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
