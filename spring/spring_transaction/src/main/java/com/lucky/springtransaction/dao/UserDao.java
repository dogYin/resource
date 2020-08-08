package com.lucky.springtransaction.dao;

import com.lucky.springtransaction.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @Description:
 * @Author: jiabin.wang
 * @Date: 2020/8/6 11:14
 */
@Mapper
public interface UserDao {

    User getUserById(Long id);

    void updateUserAge( User user);


    void insertUser(User user);

    void deleteUserById(Long id);

    List<User> listUserByParam(Integer age);
}
