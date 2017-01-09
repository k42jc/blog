package com.techeffic.blog.example.netty;

import com.techeffic.blog.example.netty.server.DiscardServer;

/**
 * 启动Netty server入口
 * @author liaoxudong
 *
 */
public class ServerBootStrap {
	
	public static void main(String[] args) {
		int port;
		if(args.length > 0){
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				port = 8088;
			}
		}else{
			port = 8088;
		}
		new DiscardServer(port).run();
	}
}
