package com.dogyin.dubbo.consumer.service;

import com.dogyin.dubbo.service.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: jiabin.wang
 * @Date: 2020/12/22 18:13
 */
@Service
public class DemoSerivceImpl {

    @Reference
    private DemoService demoService;

    public String sayHello(String name){
        return demoService.sayHello(name);
    }

}
