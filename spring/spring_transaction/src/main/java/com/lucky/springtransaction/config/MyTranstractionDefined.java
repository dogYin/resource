package com.lucky.springtransaction.config;

import org.springframework.transaction.TransactionDefinition;

/**
 * @Description:
 * @Author: jiabin.wang
 * @Date: 2020/8/7 22:10
 */
public class MyTranstractionDefined implements TransactionDefinition {

    @Override
    public int getPropagationBehavior() {
        return PROPAGATION_REQUIRES_NEW;
    }

    @Override
    public int getIsolationLevel() {
        return ISOLATION_DEFAULT;
    }
}
