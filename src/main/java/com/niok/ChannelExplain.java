package com.niok;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static java.lang.System.out;
public class ChannelExplain {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 5555);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(socketAddress);
        SocketChannel channel = null;
        while((channel=server.accept())!=null){
            out.println("--------");
            OutputStream out = channel.socket().getOutputStream();
            while(true){
                out.write("hello world!!".getBytes());
                out.flush();
            }
        }
    }
}



/*
服务端：ServerSocketChannel
 */