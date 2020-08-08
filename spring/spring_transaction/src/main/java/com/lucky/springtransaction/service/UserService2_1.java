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
 *
 * propagation 传播行为
 * rollbackFor 回滚事件
 *
 *
 * @Author: jiabin.wang
 * @Date: 2020/8/6 11:20
 */
@Service
public class UserService2_1 {

    @Autowired
    private UserDao userDao;


    @Autowired
    private UserService2_2 userService22;


    /**
     *我们在开发中经常会碰到下面
     */
    @Transactional
    public void testNull(User user1,User user2){
        try {
            userDao.updateUserAge(user1);
            userDao.updateUserAge(user2);

            //调用其他service 巴拉巴拉
            int i = 1/0;
        }catch (Exception e){
            //handle Exception
        }

    }

    /**
     * Propagation.REQUIRED
     *
     *
     *解释一下哈 ：REQUIRED如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务
     *
     * 再解释一下哈：
     *  如果外部方法没有开启事务的话，Propagation.REQUIRED修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     *  如果外部方法开启事务并且被Propagation.REQUIRED的话，所有Propagation.REQUIRED修饰的内部方法和外部方法均属于同一事务 ，只要一个方法回滚，整个事务均回滚。
     *
     *
     * 通俗一点
     *
     *
     * 废话少说，演示一下
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void testREQUIRED(User user1,User user2){

        try {
            userDao.updateUserAge(user1);
            userService22.testREQUIRED(user2);
        }catch (Exception e){

        }
    }


    /**
     *
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void testREQUIRESNEW(User user1,User user2){
        try {
            userDao.updateUserAge(user1);
            userService22.testREQUIRES_NEW(user2);
        }catch (Exception e){

        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void  testNESTED(User user1,User user2){
        try {
            userDao.updateUserAge(user1);
            userService22.testNESTEDNotException(user2);
        }catch (Exception e){

        }

    }



    @Transactional(propagation = Propagation.REQUIRED)
    public void testMANDATORY(User user1,User user2){
        try {
            userDao.updateUserAge(user1);
            userService22.testMANDATORY(user2);
        }catch (Exception e){

        }
    }
}
