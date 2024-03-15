package com.example.service.impl;

import com.example.enums.UserRole;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.pojo.UserDTO;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    // 用于存储用户登录后的token，使用ThreadLocal维护，默认情况下，ThreadLocal不会存储Null值，所以如果token为null，不会影响其他线程
    private static final ThreadLocal<String> threadLocalToken = new ThreadLocal<>();

    // 获取当前线程的token
    public static String getCurrentToken() {
        return threadLocalToken.get();
    }

    // 根据用户名查找用户
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    // 根据邮箱查找用户
    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

//    // 判断邮箱格式是否正确
//    public static boolean isEmailValid(String email) {
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
//                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//
//        Pattern pattern = Pattern.compile(emailRegex);
//        Matcher matcher = pattern.matcher(email);
//
//        return matcher.matches();
//    }

    // 注册
    @Override
    public int register(String username, String password, UserRole role, String email, String phoneNumber) {
        // 检查用户名是否已存在
        if (findByUserName(username) != null) {
            return 1;
        }
        // 检查邮箱是否已存在
        if (findByEmail(email) != null) {
            return 2;
        }
        // 检查用户名是否为空
        if (username == null) {
            return 3;
        }
        // 检查密码是否为空
        // 检查密码长度是否大于6位
        if (password == null || password.length() < 6) {
            return 4;
        }
        // 注册
        String encryptedPassword = Md5Util.getMD5String(password);
        userMapper.add(username, encryptedPassword, role, email, phoneNumber);
        return 0;
    }

    // 登录
    @Override
    public int login(String username, String password) {
        User loginuser = findByUserName(username);
        // 检查用户名不存在
        if (findByUserName(username) == null) {
            return 1;
        }
        if (Md5Util.getMD5String(password).equals(loginuser.getPassword())) {
            //签发token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginuser.getUserId());
            claims.put("username", loginuser.getUsername());
            String token = JwtUtil.genToken(claims);
            threadLocalToken.set(token);
            return 0;
        } else {
            return 2;
        }
    }


    // 查询所有用户
    @Override
    public List<UserDTO> findAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.findAll();
        // 将User对象转换为UserDTO对象
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setRole(user.getRole());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            return userDTO;
        }).collect(Collectors.toList());
        return userDTOs;
    }

    // 更新用户信息
    @Override
    public int update(User user) {
        user.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        if (userMapper.findByEmail(user.getEmail()) != null) {
            return 1;
        }
        if (user.getPhoneNumber().length() != 11) {
            return 2;
        }
        userMapper.update(user);
        return 0;
    }

    // 删除用户
    @Override
    public int delete(String username) {
        if (userMapper.findByUserName(username) != null) {
            userMapper.delete(username);
            return 0;
        } else {
            return 1;
        }
    }

}