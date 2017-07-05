package com.techeffic.blog.example.nio.server_client;

/**
 * Nio timeserver启动器
 * Created by liaoxudong on 2017/7/5.
 */
public class TimeServer {

    public static void main(String[] args) {
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(8000);
        new Thread(timeServer).start();
    }
}
