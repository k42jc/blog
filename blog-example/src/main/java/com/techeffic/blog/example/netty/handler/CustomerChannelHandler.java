package com.techeffic.blog.example.netty.handler;

import java.util.Date;

import com.techeffic.blog.example.thread.InheritableTools;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CustomerChannelHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.err.println("通道注册：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.channelRegistered(ctx);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("通道开始活动：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.channelActive(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.err.println("读取通道：ctx【"+ctx.toString()+"】，msg:【"+msg+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.channelRead(ctx, msg);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("通道闲置：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.channelInactive(ctx);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.err.println("通道读取完成：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.channelReadComplete(ctx);
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.err.println("通道未注册：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.channelUnregistered(ctx);
	}
	
	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx)
			throws Exception {
		System.err.println("通道可写状态被改变：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.channelWritabilityChanged(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.err.println("捕捉到异常：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.exceptionCaught(ctx, cause);
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.err.println("添加回调处理器：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.handlerAdded(ctx);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.err.println("回调处理器移除：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.handlerRemoved(ctx);
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		System.err.println("用户请求触发：ctx【"+ctx.toString()+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		super.userEventTriggered(ctx, evt);
	}
	
	@Override
	public boolean isSharable() {
		boolean isSharable = super.isSharable();
		System.err.println("是否共享：isSharable【"+isSharable+"】");
		System.err.println("ThreadLocal："+InheritableTools.itl.get());
		return super.isSharable();
	}
	

}
