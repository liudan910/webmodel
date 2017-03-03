package com.knowlege;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static java.lang.System.out;
public class ChannelExplain {
    private static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("http://jenkov.com",80));
        long startTime = System.currentTimeMillis();
        int cont=0;
        while (!socketChannel.finishConnect()){
            cont++;
            if((System.currentTimeMillis()-startTime)/1000 == cont){
                out.print("-----------");
            }
        }
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRread = socketChannel.read(buf);
        socketChannel.close();
    }
}



/*
1  FileChannel 不可以设置非阻塞模式
2.SocketChannel
 可以设置 SocketChannel 为非阻塞模式（non-blocking mode）.设置之后，就可以在异步模式下调用connect(), read() 和write()了。
 */