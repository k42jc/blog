package com.techeffic.blog.example.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * NIO 管道操作示例
 * Created by liaoxudong on 2017/4/18.
 */
public class PipeExample {

    public static void main(String[] args) {
        try {
            Pipe pipe = Pipe.open();

            // 往管道中写数据需要先获取管道的sinkChannel
            Pipe.SinkChannel sinkChannel = pipe.sink();

            String data = "new String to write to file..."+System.currentTimeMillis();
            ByteBuffer buffer = ByteBuffer.allocate(48);
            buffer.put(data.getBytes());

            buffer.flip();
            while (buffer.hasRemaining()) {
                sinkChannel.write(buffer);
            }

            buffer.clear();
            // 在管道中读取数据需要获取管道的sourceChannel
            Pipe.SourceChannel sourceChannel = pipe.source();
            int bytesRead = sourceChannel.read(buffer);
            /*do{
                System.err.println("still waiting...");
            }while(bytesRead >= 0);*/
            while(bytesRead != -1){
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char)buffer.get());
                }

                buffer.clear();
                bytesRead = sourceChannel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
