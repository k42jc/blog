package com.techeffic.blog.example.netty;

import com.techeffic.blog.example.netty.server.DiscardServer;

/**
 * 启动Netty server入口
 * @author liaoxudong
 *
 */
public class ServerBootStrap {
	
	public static void main(String[] args) {
		new DiscardServer(8011).run();
	}
}
