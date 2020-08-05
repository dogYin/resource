package com.yucx.exercise;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;

/**
 * 打印 jvm 中所有非系统类的 transformer
 */
public class Transformer4PrintClasses implements ClassFileTransformer {
    private static final List<String> SYSTEM_CLASS_PREFIX = Arrays.asList("java", "sum", "sun", "jdk");

    /**
     *
     * @param loader    定义要转换的类加载器；如果是引导加载器，则为 null
     * @param className 完全限定类内部形式的类名称和 The Java Virtual Machine Specification 中定义的接口名称。例如，"java/util/List"
     * @param classBeingRedefined   如果是被重定义或重转换触发，则为重定义或重转换的类；如果是类加载，则为 null
     * @param protectionDomain      要定义或重定义的类的保护域
     * @param classfileBuffer       类文件格式的输入字节缓冲区
     * @return
     * @throws IllegalClassFormatException
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!isSystemClass(className)) {
            if (null == className || className.isEmpty()) {
                return classfileBuffer;
            }

            System.out.println("************* load class " + className);

            // 通过修改返回的字节流 classfileBuffer，可以达到对某个类的修改
            /*
                JDK的规范中运行期重定义一个类必须遵循以下原则：
                    不允许新增、修改和删除成员变量
                    不允许新增和删除方法
                    不允许修改方法签名
             */

            // ************ 讲解下 ASM 工具使用 ************
        }
        return classfileBuffer;
    }

    /**
     * 判断一个类是否为系统类
     *
     * @param className 类名
     * @return System Class then return true,else return false
     */
    private boolean isSystemClass(String className) {
        // 假设系统类的类名不为NULL而且不为空
        if (null == className || className.isEmpty()) {
            return false;
        }

        for (String prefix : SYSTEM_CLASS_PREFIX) {
            if (className.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }
}
