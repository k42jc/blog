package com.techeffic.blog.example.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import java.io.File;

/**
 * 多个SourceHandler整合多个文件路径到一个Jetty 服务示例
 * 使用ContextHandlers实现同一个路径访问多个服务器文件路径
 *
 * 从配置的ContextHandlerCollection顺序加载
 * 如请求的文件在第一个目录 则直接显示
 * 否则按顺序在配置的resourceHandler中查找
 * Created by liaoxudong on 2017/5/15.
 */
public class SplitFileServer {
    public static void main(String[] args) {
        // 创建默认服务器 使用connector指定端口
        Server server = new Server();
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(8090);
        server.setConnectors(new Connector[]{serverConnector});

        //第一个ResourceHandler设置访问路径为当前工程路径
        ResourceHandler resourceHandler = new ResourceHandler();
        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/");
        //当前类执行路径 example模块
        contextHandler.setBaseResource(Resource.newResource(new File("C:\\Users\\liaoxudong\\Documents\\GitHub\\blog")));
        contextHandler.setHandler(resourceHandler);

        ResourceHandler resourceHandler1 = new ResourceHandler();
        ContextHandler contextHandler1 = new ContextHandler();
        contextHandler1.setContextPath("/");
        //指定test模块路径
        contextHandler1.setBaseResource(Resource.newResource(new File("C:\\Users\\liaoxudong\\Documents\\GitHub\\netty")));
        contextHandler1.setHandler(resourceHandler1);

        // 加入ContextHandler集合
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
        contextHandlerCollection.setHandlers(new Handler[]{contextHandler,contextHandler1});

        server.setHandler(contextHandlerCollection);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
