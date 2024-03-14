package com.example.service;

import com.example.enums.UserRole;
import com.example.pojo.User;
import com.example.pojo.UserDTO;

import java.util.List;

public interface UserService {

    //根据用户名查询用户
    User findByUserName(String username);

    //根据邮箱查询用户
    User findByEmail(String email);

    //注册用户
    int register(String username, String password, UserRole role, String email, String phoneNumber);

    //用户登录
    int login(String username, String password);

    List<UserDTO> findAll();

    int update(User user);

    int delete(String username);
}
