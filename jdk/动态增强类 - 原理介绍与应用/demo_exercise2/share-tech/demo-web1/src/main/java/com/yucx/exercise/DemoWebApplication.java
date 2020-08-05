package com.yucx.exercise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;

/**
 * description：
 *
 * @author: changxing.yu
 * @date: 2020/7/6
 */

@Slf4j
@SpringBootApplication
public class DemoWebApplication {
    public static void main(String[] args) {
        System.out.println("=================== main ( 应用: DemoWebApplication ) ===================");

        SpringApplication.run(DemoWebApplication.class, args);

        log.info("应用: DemoWebApplication, pid: {}", getPid());
    }

    public static String getPid() {
        // get name representing the running Java virtual machine.
        String name = ManagementFactory.getRuntimeMXBean().getName();
        // System.out.println(name);
        // get pid
        String pid = name.split("@")[0];
        return pid;
    }
}
