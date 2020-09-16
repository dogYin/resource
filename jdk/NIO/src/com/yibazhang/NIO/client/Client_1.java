package com.yibazhang.NIO.client;

/**
 * @Description:
 * @Author: jiabin.wang
 * @Date: 2020/8/11 13:46
 */
public class Client_1 {


    public static void main(String[] args) {
        int port = 9898;
        new Thread(new TimeClientHandle("127.0.0.1",port)).start();
    }
}
