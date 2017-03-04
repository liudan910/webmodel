package com.niok;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static java.lang.System.out;
/*
 ByteBuffer
 */

public class BufferExplain {
    public static void main(String[] args) throws IOException {
        //利用FileChannel读取数据到Buffer
        RandomAccessFile aFile = new RandomAccessFile("D:\\test.txt","rw");
        FileChannel inChannel = aFile.getChannel();
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
        aFile.close();
    }
}
/*
flip(): 当向Buffer写入数据后，读数据时，需用flip()将Buffer从写模式切换到读模式 （反转Buffer)

 */