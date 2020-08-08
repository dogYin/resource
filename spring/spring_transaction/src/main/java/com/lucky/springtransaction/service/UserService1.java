package com.lucky.springtransaction.service;

import com.lucky.springtransaction.dao.UserDao;
import com.lucky.springtransaction.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Description:  编程式事务介绍
 *
 * 编程式事务在一般开发中是用不到的，如果你有需要去手动控制事务那么可以参考下用法
 *
 * @Author: jiabin.wang
 * @Date: 2020/8/6 11:20
 */
@Service
@Slf4j
public class UserService1 {



    @Autowired
    private UserDao userDao;


    /**
     * 操作事务的api之一   有点类似于redisTemplate 和 JdbcTemplate 其实它的底层就是PlatformTransactionManager
     */
    @Autowired
    private TransactionTemplate transactionTemplate;


    /**
     * 操作事务api之二
     */
    @Autowired
    private PlatformTransactionManager transactionManager;


    /**
     * 测试 transactionTemplate api
     */
    public void test1(final User user){
        TransactionCallback callback = new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    //1.发生异常不抓捕时会自动回滚
                    userDao.updateUserAge(user);
                    int x = 4/0;
                }catch (Exception e){
                    //2.不手动指定回滚的话当前修改不会回滚
                    //3.不信的话把catch去掉试试，应该会回滚，
                    transactionStatus.setRollbackOnly();
                }
            }
        };
        transactionTemplate.execute(callback);
    }


    /**
     * 测试 transactionManager api
     */
    public void test2(User user){

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            userDao.updateUserAge(user);
            //1.事务不手动提交的话是不会更新成功的，不信你试试，这里涉及到了刷脏
            transactionManager.commit(status);
            //提交之后的回滚会有用吗？ 自己试试？刷脏完了然后回滚有用
            int x = 4/0;
        }catch (Exception e){
            //2.在这里不手动指定回滚，数据状态还是修改前的  所以这里的回滚有必要吗？
            transactionManager.rollback(status);
        }
    }
}
