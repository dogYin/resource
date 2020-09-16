package com.yibazhang.NIO.server;


/**
 * @Description:
 * @Author: jiabin.wang
 * @Date: 2020/8/10 14:12
 */
public class Server_1 {

    public static void main(String[] args) {
        int port = 8989;
        MultiplexerTimeServer  timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-TimeServer-001");
    }
}
