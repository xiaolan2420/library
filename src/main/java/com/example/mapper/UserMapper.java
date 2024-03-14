package com.example.mapper;

import com.example.enums.UserRole;
import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper{

    //根据用户名查询用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUserName(String username);

    //根据邮箱查询用户
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);

    //添加用户
    @Insert("INSERT INTO users (username, password,role,email,phone_number,create_time,update_time) " +
            " VALUES (#{username}, #{password}, #{role}, #{email}, #{phoneNumber}, now(), now() )")
    void add(String username, String password, UserRole role, String email, String phoneNumber);

    //查询所有用户
    @Select("SELECT * FROM users")
    List<User> findAll();

    @Update("UPDATE users SET email= #{email}, phone_number = #{phoneNumber}, update_time = #{updateTime} WHERE user_id = #{userId}")
    void update(User user);

    @Delete("DELETE FROM users WHERE username = #{username}")
    void delete(String username);
}
