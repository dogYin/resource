package com.yucx.exercise;

import java.lang.instrument.Instrumentation;

/**
 * description：
 *
 * @author: changxing.yu
 * @date: 2020/7/6
 */
public class AgentTest1 {

    /**
     * 启动加载
     * <p>
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     *
     * @param agentOps
     * @param inst     该实例可以 在class加载前改变class的字节码，也可以在class加载后重新加载
     */
    public static void premain(String agentOps, Instrumentation inst) {
        /*
        字节码转换器
        关于ClassFileTransformer 工作流程 看ppt里面的图：1.3 Instrumentation接口
        通过 addTransformer方法注册了一个 ClassFileTransformer(字节码转换器,可以很好的实现虚拟机层面的AOP), 后面类加载的时候都会经过这个 Transformer处理
         */
        // inst.addTransformer(ClassFileTransformer transformer);
        // inst.removeTransformer(ClassFileTransformer transformer);

        /*
        对一些已经被JVM加载过的类重新拿出来经过注册好的 字节码转换
        需要遵循重定义一个类原则
         */
        // inst.retransformClasses(Class<?>... classes);
        /*
        与如上类似，但不是重新进行转换处理，而是直接把处理结果(bytecode)直接给JVM
         */
        // inst.redefineClasses(Class<?>... classes);

        /*
        获得当前已经加载的Class，可配合 retransformClasses 使用
        演示:Transformer4CalcuMethodCostTime.java 要用到下面2个方法
         */
        // inst.getAllLoadedClasses();

        /*
        动态增加 BootClassPath / SystemClassPath 搜索路径
        boot class 加载路径（-Xbootclasspath）和 system class（-cp）加载路径
         */
        // inst.appendToBootstrapClassLoaderSearch(new JarFile("c:\\xxx.jar"));
        // inst.appendToSystemClassLoaderSearch(new JarFile("c:\\xxx.jar"));

        System.out.println("=================== premain ===================");
        System.out.println("方法: premain(String agentOps, Instrumentation inst), 参数: " + agentOps);

        doSomething(inst, "premain");
    }

    /**
     * 启动加载
     * <p>
     * 如果不存在 premain(String agentOps, Instrumentation inst)
     * 则会执行 premain(String agentOps)
     *
     * @param agentOps
     */
    public static void premain(String agentOps) {
        System.out.println("=================== premain ===================");
        System.out.println("方法: premain(String agentOps), 参数: " + agentOps);
    }

    /**
     * 动态加载
     *
     * @param featureString
     * @param inst
     */
    public static void agentmain(String featureString, Instrumentation inst) {
        System.out.println("=================== agentmain ===================");
        System.out.println("方法: agentmain(String featureString, Instrumentation inst), 参数: " + featureString);

        doSomething(inst, "agentmain");
    }

    /**
     * 动态加载
     *
     * @param featureString
     */
    public static void agentmain(String featureString) {
        System.out.println("=================== agentmain ===================");
        System.out.println("方法: agentmain(String featureString), 参数: " + featureString);
    }

    /**
     * 搞点事情
     *
     * @param inst
     */
    public static void doSomething(Instrumentation inst, String from) {
        System.out.println("方法: doSomething(Instrumentation inst)");

        // printClassInfo(inst);

        //calcuMethodCostTime(inst);
        //retransformTestServiceImpl(inst, from);
    }

    /**
     * 对jvm已加载的类 进行重新 字节码转换
     * @param inst
     * @param from
     */
    public static void retransformTestServiceImpl(Instrumentation inst, String from) {
        System.out.println("方法: retransformTestServiceImpl(), 参数(from): " + from);
        if ("agentmain".equals(from)) {
            try {
                Class<?>[] allClass = inst.getAllLoadedClasses();
                for (Class<?> aClass : allClass) {
                    // 过滤掉对于JVM认为不可修改的类
                    if (!inst.isModifiableClass(aClass)) {
                        continue;
                    }

                    String className = aClass.getName();
                    if (className != null && className.trim().equals("com.yucx.exercise.service.TestServiceImpl")) {
                        System.out.println("******* 找到class: " + aClass.getSimpleName() + ", 全路径:" + aClass.getName());
                        // inst添加字节码转换器时, 参数:canRetransform 必须为true
                        inst.retransformClasses(aClass);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印 jvm 中所有非系统类的
     *
     * @param inst
     */
    public static void printClassInfo(Instrumentation inst) {
        inst.addTransformer(new Transformer4PrintClasses(), true);
    }

    /**
     * 计算 TestServiceImpl.getRandomVal() 方法的耗时
     *
     * @param inst
     */
    public static void calcuMethodCostTime(Instrumentation inst) {
        inst.addTransformer(new Transformer4CalcuMethodCostTime(), true);
    }
}
