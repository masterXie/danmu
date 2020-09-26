package com.master.danmu;

public class Test {

    public static void main(String[] args) {
        int contentLen = 0;
        int[] is = new int[]{63,0,0,0};
        for(int i= 0 ;i<4;i++){
            contentLen += is[i] * Math.pow(16,2*i);
        }
        System.out.println(contentLen);
    }
}
