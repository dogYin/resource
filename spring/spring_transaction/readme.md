平时我们都是怎样理解事务的呢？<br/>
看资料？看博客？看书？mysql多事务并发，难以操作，所以今天我来了，带着spring走来了<br/>

事务隔离级别对照着ppt操作

    -------src
        ---main
            ---com.lucky.springtransaction
                ---config
                    MyTranstractionDefined.class     --------自定义事务 设置传播属性为PROPAGATION_REQUIRES_NEW
                ---dao
                    UserDao.class                    -------- userDAO
                ---pojo
                    User.class                       -------- User
                ---service
                    UserService0_1                   -------- 事务隔离级别
                    UserService0_2                   -------- 事务隔离级别
                    UserService1                     -------- spring操作事务api
                    UserService2_1                   -------- spring事务传播特性
                    UserService2_2                   -------- spring事务传播特性                  
        ---test
            ---IsLoactionTest.class                  -------- 隔离级别测试     
            ---PropagationTest.class                 -------- 事务传播测试（重点）
        

