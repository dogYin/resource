package com.lucky.springtransaction.service;

import com.lucky.springtransaction.config.MyTranstractionDefined;
import com.lucky.springtransaction.dao.UserDao;
import com.lucky.springtransaction.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: jiabin.wang
 * @Date: 2020/8/7 18:17
 */
@Service
@Slf4j
public class UserService0_2 {


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService0_1 userService01;

    @Autowired
    private PlatformTransactionManager transactionManager;


    /**
     * 为什么要用编程式 事务呢！  自己去体会
     * @param user
     */
    public void testReadUncommited(User user){
        TransactionDefinition transactionDefinition = new MyTranstractionDefined();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            userDao.updateUserAge(user);

            userService01.getUserById(user);
            int i = 1/0;
            transactionManager.commit(status);
        }catch (Exception e){
            transactionManager.rollback(status);
        }
    }



    @Transactional(propagation = Propagation.REQUIRED)
    public void testReadCommitted(User user1) throws InterruptedException {

        User id = userDao.getUserById(user1.getId());
        log.error("first read:"+id.getAge());

        userService01.updateUser(user1);
        Thread.sleep(5000);


        User id1 = userDao.getUserById(user1.getId());
        log.error("second read:"+id1.getAge());


    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void testREPEATABLEREAD(Integer age,User user) throws InterruptedException {
        List<User> users = userDao.listUserByParam(age);
        String first = users.stream().map(User::getName).collect(Collectors.joining(","));
        log.error("first read:"+first);

        userService01.insertUser(user);
        Thread.sleep(5000);

        List<User> users1 = userDao.listUserByParam(age);
        String second = users1.stream().map(User::getName).collect(Collectors.joining(","));
        log.error("second read:"+second);
    }

}
