package com.yucx.exercise.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * description：
 *
 * @author: changxing.yu
 * @date: 2020/7/6
 */

@Slf4j
@Service
public class TestServiceImpl {

    /**
     * sleep后返回 randomVal
     *
     * @param randomVal
     * @return
     */
    public Integer getRandomVal(Integer randomVal) {
        try {
            // sleep, 模拟耗时
            Thread.sleep(randomVal);
        } catch (InterruptedException e) {
            log.error("sleep error:", e);
        }

        return randomVal;
    }
}
