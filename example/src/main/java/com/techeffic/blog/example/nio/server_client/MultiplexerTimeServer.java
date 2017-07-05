package com.techeffic.blog.example.nio.server_client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 简单时间服务器的多路复用器
 * Created by liaoxudong on 2017/7/5.
 */
public class MultiplexerTimeServer implements Runnable{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop = false;

    public MultiplexerTimeServer(int port) {
        try {
            //创建多路复用器
            selector = Selector.open();
            //打开ServerSocketChannel，用于监听客户端连接，是所有客户端连接的父管道
            serverSocketChannel = ServerSocketChannel.open();
            //设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //绑定连接端口，并设置最大连接数为1024
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            //将serverSocketChannel注册到selector，并监听ACCEPT事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务初始化...");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 设置停止标识为true
     */
    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                //获取待处理的key集合，返回值表示当前有多少个通道就绪,当检测到存在就绪的通道时才处理
                int num = selector.select(1000);
                if(num > 0){
                    //访问已就绪的通道集合，遍历判断属于何种事件
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        // selector不会自己从已选择键集中移除SelectionKey实例，需要手动移除。以便下次通道变成就绪时，获取新的就绪键集
                        keyIterator.remove();
                        try {
                            handlerInput(key);
                        } catch (Exception e) {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //关闭多路复用器，多路复用器关闭后，所有注册在上面的channel和pipe等资源都会自动去注册并关闭
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理具体的通道事件
     * @param key
     */
    private void handlerInput(SelectionKey key) throws Exception{
        if(key.isValid()){
            if (key.isConnectable()) {//当前就绪通道连接就绪
                // 有远程连接到当前通道事件
            } else if (key.isAcceptable()) {// 处理接收到的请求内容
                // 获取接收到的连接并注册到多路复用器且监听读取事件
                ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
                SocketChannel channel = serverChannel.accept();
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            } else if (key.isReadable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer allocate = ByteBuffer.allocate(1024);
                // 将通道中待处理信息写入buffer
                int read = channel.read(allocate);
                if(read > 0){//写入成功
                    // 从写模式转换为读模式
                    allocate.flip();
                    // 从buffer中读取并处理请求数据
                    byte[] bytes = new byte[allocate.remaining()];
                    allocate.get(bytes);
                    String s = new String(bytes, Charset.defaultCharset());
                    System.out.println("服务器收到命令："+s);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String back = "query time".equalsIgnoreCase(s) ? simpleDateFormat.format(new Date(System.currentTimeMillis())) : "请求错误！";
                    doWrite(channel,back);
                }
            }
        }
    }

    /**
     * 响应客户端
     * @param channel
     * @param response
     */
    private void doWrite(SocketChannel channel, String response) throws Exception{
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
        }
    }
}
