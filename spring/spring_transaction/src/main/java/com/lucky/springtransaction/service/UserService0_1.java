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

/**
 * @Description:
 * @Author: jiabin.wang
 * @Date: 2020/8/7 18:13
 */
@Service
@Slf4j
public class UserService0_1 {


    @Autowired
    private UserDao userDao;

    @Autowired
    private PlatformTransactionManager transactionManager;


    public void getUserById(User user) throws InterruptedException {
        TransactionDefinition transactionDefinition = new MyTranstractionDefined();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        User id1 = userDao.getUserById(user.getId());
        log.error("---------------"+id1.getName()+"->"+id1.getAge()+"---------");
        transactionManager.commit(status);
    }



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateUser(User user){
        userDao.updateUserAge(user);
        log.error("更新成功");
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertUser(User user){
        userDao.insertUser(user);
        log.error("插入成功");
    }



}
