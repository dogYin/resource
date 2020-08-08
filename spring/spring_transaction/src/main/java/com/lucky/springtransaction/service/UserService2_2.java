package com.lucky.springtransaction.service;

import com.lucky.springtransaction.dao.UserDao;
import com.lucky.springtransaction.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 声明式事务
 *
 *  声明式事务是我们日常开发中最常见的，但是我们真的了解@Transtaction注解中那些参数的含义吗
 *
 * @Author: jiabin.wang
 * @Date: 2020/8/6 11:20
 */
@Service
public class UserService2_2 {

    @Autowired
    private UserDao userDao;



    public void testNULL(User user){
        userDao.updateUserAge(user);
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testREQUIRED(User user){
        try {
            userDao.updateUserAge(user);
            int i = 1/0;
        }catch (Exception e){

        }

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testREQUIRES_NEW(User user){
        userDao.updateUserAge(user);
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.SUPPORTS,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testSUPPORTS(User user){
        userDao.updateUserAge(user);
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.MANDATORY,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testMANDATORY(User user){
        userDao.updateUserAge(user);
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.MANDATORY,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testMANDATORYNotException(User user){
        userDao.updateUserAge(user);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testNOT_SUPPORTED(User user){
        userDao.updateUserAge(user);
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.NEVER,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testNEVER(User user){
        userDao.updateUserAge(user);
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.NESTED,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testNESTED(User user){
        userDao.updateUserAge(user);
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.NESTED,isolation= Isolation.DEFAULT,rollbackFor = Exception.class)
    public void testNESTEDNotException(User user){
        userDao.updateUserAge(user);
    }

}
