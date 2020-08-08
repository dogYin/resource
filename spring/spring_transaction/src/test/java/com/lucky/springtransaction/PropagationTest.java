package com.lucky.springtransaction;

import com.lucky.springtransaction.pojo.User;
import com.lucky.springtransaction.service.UserService2_1;
import com.lucky.springtransaction.service.UserService1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PropagationTest {

    @Autowired
    private UserService2_1 userService21;

    @Autowired
    private UserService1 userService1;


    /**
     * 测试编程式事务之 transactionTemplate
     */
    @Test
    public void testUserServiceTest1(){
        User user = new User();
        user.setId(1l);
        user.setAge(22);
        userService1.test1(user);
    }



    /**
     * 测试编程式事务之 transactionManager
     */
    @Test
    public void testUserServiceTest2(){
        User user = new User();
        user.setId(1l);
        user.setAge(22);
        userService1.test2(user);
    }


    /**
     *
     */
    @Test
    public void testNuLL(){
        User user1 = new User();
        user1.setId(1L);
        user1.setAge(2);


        User user2 = new User();
        user2.setId(1L);
        user2.setAge(2);

        userService21.testNull(user1,user2);
    }



    /**
     * 我们分三步进行
     * 1.首先将被调用方法中的事务注解去掉 -> 查看产生的后果
     *
     * 结果：去掉注解后 主调用方法 不会回滚 更新成功，被调方法也更新成功
     * 结论：，说明一条sql过去就是一个事务，这就是不加注解的后果
     *
     *
     * 2.给被调用方法加上注解 - > 查看产生后果
     * 结果 ：加上注解后都会回滚而且抛出UnexpectedRollbackException：Transaction rolled back because it has been marked as rollback-only
     * 结论：为什么会抛出这个异常呢，因为事务两个公用了一个事务，被调用方法已经标志当前事务应该回滚
     *
     * 3.被调的异常在被调那边抓住 -> 查看后果
     * 都更新成功，为啥？
     * 你注解都声明了异常回滚 异常回滚，你都不抛异常  回滚个锤子
     *
     *
     * 4.总结
     *
     * 被调方法和主调方法都用REQUIRED修饰 那么就是两个方法在一个事务中执行，有一个回滚则都回滚，回滚时要显视的看到异常抛出
     *
     *
     *
     *
     */
    @Test
    public void testREQUIRED(){
        User user1 = new User();
        user1.setId(1L);
        user1.setAge(2);


        User user2 = new User();
        user2.setId(2L);
        user2.setAge(2);
        userService21.testREQUIRED(user1,user2);
    }


    /**
     * 接下来我们都是分三步
     * 1.都更新成功，结论同上
     *
     * 2.两个注解都为REQUIRED_NEW 或者 一个为REQUIRED一个为REQUIRED_NEW
     *
     * 结论：主调方法更新成功  被调更新失败
     *
     *
     * 3.都更新成功，结论同上，都是锤子
     *
     *
     * 4.总结  创建一个新的事务，如果当前存在事务，则把当前事务挂起。
     * 也就是说不管外部方法是否开启事务，Propagation.REQUIRES_NEW修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     *
     */
    @Test
    public void testREQUIRES_NEW(){
        User user1 = new User();
        user1.setId(1L);
        user1.setAge(2);


        User user2 = new User();
        user2.setId(2L);
        user2.setAge(2);
        userService21.testREQUIRESNEW(user1,user2);
    }


    /**
     * 两步走
     *
     * 1.主调方法不加开启事务  被调开启
     *
     *结果：主调方法不会滚，被调方法回滚
     *结论：如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED 且开启的事务相互独立，互不干扰
     *
     * 2.主调方法开启事务，被调也开启
     *  2.1 在UserService2_1.testNESTED(让方法抛出异常，让被调方法在前面)方法中调用UserService2_2.testNESTEDNotException(测试主调回滚，被调也回滚)
     *
     *      结果1：主调回滚 被调也回滚
     *
     *  2.1 在UserService2_1.testNESTED（加上catch块）方法中调用UserService2_2.testNESTED(测试被调回滚，主调不会滚)
     *
     *       结果2：主调不回滚 被调回滚
     *
     *
     *
     * 3.总结
     *
     * 如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；
     * 如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED 且开启的事务相互独立，互不干扰
     *
     *
     *
     */
    @Test
    public void testNESTED(){
        User user1 = new User();
        user1.setId(1L);
        user1.setAge(2);


        User user2 = new User();
        user2.setId(2L);
        user2.setAge(2);
        userService21.testNESTED(user1,user2);
    }


    /**
     *
     * 1.去掉主调的事务声明，调用非异常版被调(UserService2_2.testMANDATORYNotException)
     *
     * 注意：去掉主调的catch块哈，不然主调就更新成功了
     *  结果：抛出异常 IllegalTransactionStateException：No existing transaction found for transaction marked with propagation 'mandatory'
     *  结论：主调不存在事务抛出异常
     *
     * 2.加上主调事务，调用异常版被调（UserService2_2.testMANDATORY）
     *
     * 结果：抛出异常UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
     *
     * 结论：加入主调事务，当前事务已经被标记异常，提交时检测到，所以会抛出异常
     *
     *
     *结论：如果主调存在事务则加入，如果不存在则抛出异常
     *
     *
     */
    @Test
    public void testPROPAGATIONMANDATORY(){
        User user1 = new User();
        user1.setId(1L);
        user1.setAge(2);


        User user2 = new User();
        user2.setId(2L);
        user2.setAge(2);
        userService21.testMANDATORY(user1,user2);
    }
}
