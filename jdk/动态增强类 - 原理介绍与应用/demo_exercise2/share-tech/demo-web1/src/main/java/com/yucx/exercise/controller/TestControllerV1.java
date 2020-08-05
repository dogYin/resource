package com.yucx.exercise.controller;

import com.google.common.collect.Maps;
import com.yucx.exercise.service.TestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

/**
 * description：
 *
 * @author: changxing.yu
 * @date: 2020/7/6
 */

@Slf4j
@RestController
@RequestMapping("/test/v1")
public class TestControllerV1 {

    @Resource
    private TestServiceImpl testService;

    @RequestMapping("/index1")
    @ResponseBody
    public Map<Object, Object> index1(HttpServletRequest request) {
        Map<Object, Object> attachments = Maps.newHashMap();

        Integer randomVal = new Random().nextInt(2000);
        if (randomVal < 1000) {
            randomVal = null;
        }
        try {
            attachments.put("randomVal", testService.getRandomVal(randomVal));
        } catch (Exception e) {
            attachments.put("msg", "发生异常...");
        }

        return attachments;
    }
}
