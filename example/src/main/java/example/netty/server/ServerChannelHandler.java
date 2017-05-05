package example.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerChannelHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("连接启动：ctx【"+ctx.toString()+"】");
		/*System.err.println("ThreadLocal："+InheritableTools.itl.get());*/
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.err.println("读取消息：ctx【"+ctx.toString()+"】，msg:【"+msg+"】");
		//响应客户端
		ctx.writeAndFlush("收到消息，正在处理...");
		String req = (String)msg;
		System.out.println("消息内容："+req);
		Thread.sleep(1000);
		ctx.writeAndFlush("处理完成!");
		/*System.err.println("ThreadLocal："+InheritableTools.itl.get());*/
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.err.println("捕捉到异常：ctx【"+ctx.toString()+"】");
		/*System.err.println("ThreadLocal："+InheritableTools.itl.get());*/
	}
	

}
