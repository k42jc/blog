package com.techeffic.blog.example.nio.server_client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Nio 客户端异步处理
 * Created by liaoxudong on 2017/7/5.
 */
public class TimeClientHandler implements Runnable{

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop = false;
    private String order = "query time";

    public TimeClientHandler(String host, int port) {
        this.host = host == null?"127.0.0.1":host;
        this.port = port;

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (!stop) {
            try {
                int prepared = selector.select(1000);
                if (prepared > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        try {
                            handlerInput(selectionKey);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (selectionKey != null) {
                                selectionKey.cancel();
                                if(selectionKey.channel() != null)
                                    selectionKey.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理客户端接收到请求
     * @param selectionKey
     */
    private void handlerInput(SelectionKey selectionKey) throws Exception{
        if (selectionKey.isValid()) {
            //判断是否连接成功
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            if(selectionKey.isConnectable() && channel.finishConnect()){
                channel.register(selector, SelectionKey.OP_READ);
                doWrite(channel);
            } else if (selectionKey.isReadable()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int read = channel.read(byteBuffer);
                if(read > 0){
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes, Charset.defaultCharset());
                    System.out.println("服务器返回时间："+body);
                    this.stop = true;
                } else if (read < 0) {
                    selectionKey.cancel();
                    channel.close();
                }

            }
        }
    }

    /**
     * 回写数据到服务端
     * @param channel
     */
    private void doWrite(SocketChannel channel) throws Exception{
        byte[] bytes = order.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        channel.write(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            System.out.println("请求命令发送成功");
        }
    }

    /**
     * 与服务器建立连接
     */
    private void doConnect() throws Exception{

        //如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            System.out.println("连接成功...");
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        }else{
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }
}
