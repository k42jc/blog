package com.techeffic.blog.example.netty;

import com.techeffic.blog.example.netty.server.NettyServer;

/**
 * 启动Netty server入口
 * @author liaoxudong
 *
 */
public class ServerBootStrap {
	
	public static void main(String[] args) {
		new NettyServer(8011).run();
	}
}
