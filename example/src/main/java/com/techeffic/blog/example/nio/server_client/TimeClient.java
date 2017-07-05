package com.techeffic.blog.example.nio.server_client;

/**
 * 时间服务器客户端
 * Created by liaoxudong on 2017/7/5.
 */
public class TimeClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8000;
        new Thread(new TimeClientHandler(host,port)).start();
    }
}
