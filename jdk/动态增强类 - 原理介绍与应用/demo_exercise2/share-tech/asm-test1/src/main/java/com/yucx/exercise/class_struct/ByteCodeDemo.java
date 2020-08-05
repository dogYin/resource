package com.yucx.exercise.class_struct;

/**
 * description：
 * 【编译】
 * 指定编译编码, 防止编译报 编码GBK的不可映射字符
 * javac -encoding UTF-8 ByteCodeDemo.java
 * <p>
 * 【查看 .class 文件的内容】
 * javap -verbose ByteCodeDemo.class
 *
 * @author: changxing.yu
 * @date: 2020/7/9
 */
public class ByteCodeDemo {
    private int sex;
    private String name;
    public Boolean flag;
    public static int age;

    static {
        System.out.println("run in static block");
    }

    public int addAge() {
        int b = 2;
        int temp = age + b;
        System.out.println(temp);
        return temp;
    }

    public void sayHello(String name) {
        System.out.println("hello, " + name);
    }

    public static int getAge() {
        return age;
    }
}
