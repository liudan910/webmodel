package com.knowlege;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liudan19 on 2017/3/3.
 */
public class SelectorExplain {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();    //Selector创建
        ServerSocketChannel channel = ServerSocketChannel.open(); //Channel创建
        channel.socket().bind(new InetSocketAddress(9999));
        channel.configureBlocking(false);  //非阻塞模式
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ);
        int interestSet = selectionKey.interestOps();
        int readySet = selectionKey.readyOps();
        selectionKey.channel();
        selectionKey.selector();
        Set selectKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectKeys.iterator();
        while(keyIterator.hasNext()){
            SelectionKey key = keyIterator.next();
            if(key.isAcceptable()){

            }
        }
    }
}
