package com.book.concurrent.producer.consumer;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import static java.lang.System.out;

/**
 * Created by liuda on 2017/1/1.
 * 桌面搜索应用程序中的生产者任务和消费者任务
 */
public class FileCrawler implements Runnable{
    private final BlockingQueue<File> fileQueue;      //文件队列
    private final FileFilter fileFilter;              //文件过滤器
    private final File root;                          //文件目录
    public FileCrawler(BlockingQueue<File> fileQueue,FileFilter fileFilter,File root){
        this.fileQueue = fileQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }
    public void run(){
        try{
            crawl(root);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    private void crawl(File root) throws InterruptedException{
        File[] entries = root.listFiles(fileFilter);
        if(entries !=null){
            for(File entry:entries){
                if(entry.isDirectory())
                    crawl(entry);
                else if(!alreadyIndexed(entry))
                    fileQueue.put(entry);
            }
        }
    }
    private boolean alreadyIndexed(File file){
        return fileQueue.contains(file);
    }
    public static void main(String[] args) throws InterruptedException {
        File[] roots = new File[5];
        roots[0] = new File("D:\\Workspace\\waresupport\\waresupport-common");
        roots[1] = new File("D:\\Workspace\\waresupport\\waresupport-dao");
        roots[2] = new File("D:\\Workspace\\waresupport\\waresupport-domain");
        roots[3] = new File("D:\\Workspace\\waresupport\\waresupport-manager");
        roots[4] = new File("D:\\Workspace\\waresupport\\waresupport-service");
        startIndexing(roots);
    }
    public static void startIndexing(File[] roots) throws InterruptedException {
        int BOUND = 100;
        int N_COUNSUMERS = 10;
        BlockingQueue<File> queue = new LinkedBlockingDeque<File>(BOUND); //任务队列
        FileFilter filter = new FileFilter() {                       //实现文件过滤器
            public boolean accept(File pathname) {
                return true;
            }
        };
        for(File root:roots){
            new Thread(new FileCrawler(queue,filter,root)).start();   //抓取文件 - 生产者
        }
        Map<String,File> fileIndexMap = new HashMap<String,File>();
        for(int i = 0; i<N_COUNSUMERS;i++){
            new Thread(new Indexer(queue,fileIndexMap)).start();                  //给文件加索引 - 消费者
        }
        Thread.sleep(5000);
        for(String key : fileIndexMap.keySet()){
            out.println(key + " : " + fileIndexMap.get(key) );
        }
        out.println(fileIndexMap.size());
        System.exit(0);
    }
}
class Indexer implements Runnable {
    private final BlockingQueue<File> queue;
    private Map<String,File> fileIndexMap;
    public Indexer(BlockingQueue<File> queue,Map<String,File> fileIndexMap){
        this.queue = queue;
        this.fileIndexMap = fileIndexMap;
    }
    public void run(){
        try{
            while(true)
                indexFile(queue.take());
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    private void indexFile(File file){
        fileIndexMap.put(file.getName(),file);
    }
}
