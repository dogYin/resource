package com.yucx.exercise.calcu_method_cost_time;

import java.util.Random;

/**
 * description：
 * 【编译】
 * 指定编译编码, 防止编译报 编码GBK的不可映射字符
 * javac -encoding UTF-8 TestServiceImpl.java
 * <p>
 * 【查看 .class 文件的内容】 -- jvm指令:https://www.cnblogs.com/strinkbug/p/5019482.html
 * javap -verbose TestServiceImpl.class
 * javap -verbose TestServiceImpl2.class
 *
 * @author: changxing.yu
 * @date: 2020/7/6
 */
public class TestServiceImpl {
    /**
     * 返回 maxRandom 以内的随机值
     *
     * @param maxRandom
     * @return
     */
    public Integer getRandomVal(Integer maxRandom) {
        // long startTime = System.currentTimeMillis();

        if (maxRandom == null || maxRandom < 500) {
            maxRandom = 500;
        }

        int randomVal = new Random().nextInt(maxRandom);
        try {
            // sleep, 模拟耗时
            Thread.sleep(randomVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("getRandomVal error:" + e.getMessage());
        }

        /*long endTime = System.currentTimeMillis() - startTime;
        System.out.println("The cost time of getRandomVal is " + endTime);*/

        return randomVal;
    }
}
