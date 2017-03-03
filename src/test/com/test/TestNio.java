package com.test;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import static java.lang.System.out;
/**
 * Created by liudan19 on 2016/12/22.
 */
public class TestNio {
    public static void main(String[] args) throws IOException {
        //利用FileChannel读取数据到Buffer
        test3();
        RandomAccessFile aFile = new RandomAccessFile("D:\\test.txt","rw");
        FileChannel inChannel = aFile.getChannel();
       // test2(inChannel);
        aFile.close();
    }
    public static void test3() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("D:\\test.txt","rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("D:\\test2.txt","rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel,position,count);
    }
    public static void test2( FileChannel channel) throws IOException {
        ByteBuffer header = ByteBuffer.allocate(50);
        ByteBuffer body   = ByteBuffer.allocate(48);

        ByteBuffer[] bufferArray = { header, body };
        channel.read(bufferArray);
        outBuffer(header);
        out.println("-----------------");
        outBuffer(body);
    }
    public static void outBuffer(ByteBuffer buf){
        buf.flip();
        while (buf.hasRemaining()) {
            out.print((char) buf.get());    //从Buffer中读取数据
        }
    }
    public static void test1( FileChannel inChannel) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);  //将数据读到Buffer
        while (bytesRead != -1) {
            out.println("Read " + bytesRead);
            buf.flip();                        //反转Buffer
            while (buf.hasRemaining()) {
                out.print((char) buf.get());    //从Buffer中读取数据
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }

    }
}
