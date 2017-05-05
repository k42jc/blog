package com.techeffic.blog.example.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * Netty服务端
 * 
 * @author liaoxudong
 *
 */
public class NettyServer {
	private int port;

	public NettyServer(int port) {
		this.port = port;
	}

	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {

			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline()
									.addLast(
											new StringDecoder(Charset
													.forName("UTF-8")))//解码收到的客户端请求消息
									.addLast(
											new StringEncoder(Charset
													.forName("UTF-8")))//转码发回客户端的消息
									.addLast(new ServerChannelHandler());
						};
					});

			// 绑定端口 启动服务
			ChannelFuture future = boot.bind(port).sync();
			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to
			// gracefully
			// shut down your server.
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {// 优雅关闭
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
