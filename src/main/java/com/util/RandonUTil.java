package com.util;

/**
 * Created by liuda on 2017/1/1.
 */
public class RandonUTil {

    public static String getRandomKey(){
        String sample = "ABCDEFGHIJKLMNOPQRSTUCWXYZ";
        int x = 0;
        while((x=getRandom())<2){
        }
        return sample.substring(x-2,x);
    }
    public static int getRandom(){
        return (int)(Math.random()*26);
    }
}
