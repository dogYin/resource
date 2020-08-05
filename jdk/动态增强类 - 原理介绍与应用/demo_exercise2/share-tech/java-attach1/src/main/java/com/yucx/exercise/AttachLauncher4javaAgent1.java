package com.yucx.exercise;

import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.lang3.StringUtils;

/**
 * java-agent1 的 attach方式 启动器
 */
public class AttachLauncher4javaAgent1 {

    public AttachLauncher4javaAgent1(final String targetJvmPid,
                                     final String agentJarPath,
                                     final String args) throws Exception {
        // 代表一个 Java 虚拟机
        VirtualMachine vmObj = null;
        try {
            // 通过给attach方法传入一个jvm的pid（进程id），远程连接到jvm上
            vmObj = VirtualMachine.attach(targetJvmPid);
            if (vmObj != null) {
                // 通过loadAgent方法向jvm注册一个代理程序agent
                // 在该agent的代理程序中会得到一个Instrumentation实例
                vmObj.loadAgent(agentJarPath, args);
            }
        } finally {
            if (null != vmObj) {
                vmObj.detach();
            }
        }
    }

    /**
     * 内核启动程序
     *
     * @param args 参数
     *             [0] : PID
     *             [1] : agent.jar's value
     *             [2] : token
     */
    public static void main(String[] args) {
        try {
            /*
                14544 E:\\ucarinc_workspace\\self\\demo_exercise2\\share-tech\\java-agent1\\target\\java-agent1-jar-with-dependencies.jar param1=val1;param2=val2
             */
            args = new String[3];
            args[0] = "8456";
            args[1] = "E:\\ucarinc_workspace\\self\\demo_exercise2\\share-tech\\java-agent1\\target\\java-agent1-jar-with-dependencies.jar";
            args[2] = "param1=val1;param2=val2";

            // check args
            if (args.length != 3
                    || StringUtils.isBlank(args[0])
                    || StringUtils.isBlank(args[1])
                    || StringUtils.isBlank(args[2])) {
                throw new IllegalArgumentException("illegal args");
            }
            System.out.println("应用: java-attach1");
            System.out.println("args[0] = " + args[0]);
            System.out.println("args[1] = " + args[1]);
            System.out.println("args[2] = " + args[2]);

            new AttachLauncher4javaAgent1(args[0], args[1], args[2]);
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            System.err.println("sandbox load jvm failed : " + getCauseMessage(t));
            System.exit(-1);
        }
    }

    /**
     * 获取异常的原因描述
     *
     * @param t 异常
     * @return 异常原因
     */
    public static String getCauseMessage(Throwable t) {
        if (null != t.getCause()) {
            return getCauseMessage(t.getCause());
        }
        return t.getMessage();
    }
}
