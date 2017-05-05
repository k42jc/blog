package com.techeffic.blog.example.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * NIO FileChannel 示例
 * Created by liaoxudong on 2017/4/18.
 */
public class FileChannelExample {

    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("test.txt", "rw");
            // 获取文件通道 随意指定的测试文件 获取到的为空
            FileChannel fileChannel = randomAccessFile.getChannel();
            // 往通道中写入数据
            String data = "写入数据到文件通道..." + System.currentTimeMillis();
            ByteBuffer buffer = ByteBuffer.allocate(48);
            buffer.put(data.getBytes());
            buffer.flip();

            while (buffer.hasRemaining()){
                int byteWrite = fileChannel.write(buffer);
            }
            fileChannel.force(true);// 将文件持久化到硬盘
            System.out.println("文件大小："+fileChannel.size());
            fileChannel.close();

            // 从通道中读取数据
            FileChannel channel = new RandomAccessFile("test.txt", "rw").getChannel();
            Charset charset = Charset.forName("UTF-8");//Java.nio.charset.Charset处理了字符转换问题。它通过构造CharsetEncoder和CharsetDecoder将字符序列转换成字节和逆转换。
            CharsetDecoder decoder = charset.newDecoder();
            buffer.clear();
//            ByteBuffer buff = ByteBuffer.allocate(48);
            // 用于处理ByteBuffer读取乱码的问题
            CharBuffer cb = CharBuffer.allocate(48);
            int byteRead = channel.read(buffer);
            while (byteRead != -1){
                buffer.flip();
                decoder.decode(buffer,cb,false);
                cb.flip();
                while(cb.hasRemaining()){
                    System.out.print(cb.get());
                }

                buffer.clear();
                cb.clear();
                byteRead = channel.read(buffer);
            }
            channel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
