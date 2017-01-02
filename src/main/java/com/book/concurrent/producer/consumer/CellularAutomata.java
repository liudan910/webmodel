package com.book.concurrent.producer.consumer;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by liuda on 2017/1/2.
 */
class Board{
    int x,y,value;
    int maxX = 10;
    int maxY = 20;
    boolean converged;
    public void setNewValue(int x,int y,int value){
        this.x = x;
        this.y = y;
        this.value = value;
        if(value>maxX*maxY){
            converged = true;
        }
    }
    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public boolean hasConverged(){
        return converged;
    }
    public void waitForConvergence(){

    }
}

public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;
   public CellularAutomata(Board board){
       this.mainBoard = board;
       int count = Runtime.getRuntime().availableProcessors();
       this.barrier = new CyclicBarrier(count, new Runnable() {
           public void run() {
              //Integer commitNewValues();
           }
       });
       this.workers = new Worker[count];
       for(int i = 0;i<count;i++){
          // workers[i] = new Worker();
       }
   }
    public void start(){
        for(int i =0;i<workers.length;i++)
            new Thread(workers[i]).start();
        mainBoard.waitForConvergence();
    }
    private class Worker implements Runnable{
        private final Board board;
        public Worker(Board board){ this.board = board;}
        public void run(){
            while(!board.hasConverged()){
                for(int x = 0; x<board.getMaxX();x++)
                    for(int y = 0; y<board.getMaxY();y++)
                        board.setNewValue(x,y,computeValue(x,y));
            }
        }
        public int computeValue(int x,int y){
            return x*y;
        }
    }
}
