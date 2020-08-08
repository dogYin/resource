package com.lucky.springtransaction;

/**
 * @Description:
 * @Author: jiabin.wang
 * @Date: 2020/8/7 21:44
 */

import com.lucky.springtransaction.pojo.User;
import com.lucky.springtransaction.service.UserService0_1;
import com.lucky.springtransaction.service.UserService0_2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class IsLoactionTest {


    @Autowired
    private UserService0_2 userService02;

    @Autowired
    private UserService0_1 userService01;



    /**
     * 在这里将老王年龄设置为2，
     * 记得设置数据库隔离级别
     * SET GLOBAL TRANSACTION ISOLATION LEVEL READ UNCOMMITTED| REPEATABLE READ;
     * SHOW VARIABLES LIKE 'transaction_isolation';
     *
     *
     * 结论 ： 不出意外的话 当隔离级别为 REPEATABLE READ 控制台会输出1  当隔离级别为READ UNCOMMITTED 控制台会输出2
     *
     */
    @Test
    public void testREADUNCOMMITED(){
        User user = new User();
        user.setId(1L);
        user.setAge(2);
        userService02.testReadUncommited(user);
    }


    /**
     * 如果你用mybatis的话  会有个问题  这里我不说，你自己想一下会有什么问题
     */
    @Test
    public void testREADCOMMITTED() throws InterruptedException {
        User user1 = new User();
        user1.setId(1L);
        user1.setAge(2);
        userService02.testReadCommitted(user1);

    }


    /**
     * 是不是大失所望了，没达到效果，哈哈哈
     *
     * 留个作业 这个接下来有空自己去试
     * @throws InterruptedException
     */
    @Test
    public void testREPEATABLEREAD() throws InterruptedException {
        User user = new User();
        user.setAge(4);
        user.setName("老李");
        userService02.testREPEATABLEREAD(0,user);
    }

}
