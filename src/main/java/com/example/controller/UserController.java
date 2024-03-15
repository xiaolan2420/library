package com.example.controller;

import com.example.enums.UserRole;
import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.pojo.UserDTO;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 注册
    @PostMapping("/register")
    public Result register(String username, String password, UserRole role, String email, String phoneNumber) {

        if (username != null && password != null && password.length() >= 6) {
            int code = userService.register(username, password, role, email, phoneNumber);
            switch (code) {
                case 0:
                    return Result.success("注册成功");
                case 1:
                    return Result.error("用户名已存在");
                case 2:
                    return Result.error("邮箱已存在");
                case 3:
                    return Result.error("用户名为空");
                case 4:
                    return Result.error("密码少于6位");
                default:
                    return Result.error("未知错误");
            }
        } else {
            return Result.error("密码太短");
        }
    }

    // 登录
    @PostMapping("/login")
    public Result login(String username, String password) {

        int code = userService.login(username, password);
        String currentToken = UserServiceImpl.getCurrentToken();
        // 登录成功后，返回一个包含用户信息和token的Result对象
        switch (code) {
            case 0:
                return Result.success(currentToken);
            case 1:
                return Result.error("用户名不存在");
            case 2:
                return Result.error("密码错误");
            default:
                return Result.error("未知错误");
        }
    }

    // 获取用户列表
    @GetMapping("/list")
    public Result list(int pageNum,int pageSize) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        // 根据用户名查询用户信息
        User user = userService.findByUserName(username);
        // 判断用户角色
        if (user.getRole().toString().equals("admin") || user.getRole().toString().equals("staff")) {
            List<UserDTO> userDTOs = userService.findAll(pageNum,pageSize);
            // 返回一个包含用户列表的Result对象
            if (userDTOs != null) {
                return Result.success(userDTOs);
            } else {
                return Result.error("用户列表为空");
            }
        }
        return Result.error("权限不足");
    }

    // 查找用户
    @GetMapping("/retrieve")
    public Result<User> retrieve(String username) {
        // 根据用户名查询用户信息
        User user = userService.findByUserName(username);
        // 返回一个包含用户信息的Result对象
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户不存在");
        }
    }

    // 获取用户信息
    @GetMapping("/userinfo")
    public Result<User> userInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        // 根据用户名查询用户信息
        User user = userService.findByUserName(username);
        // 返回一个包含用户列表的Result对象
        return Result.success(user);
    }

    // 修改用户信息
    @PutMapping("/update")
    public Result update(@RequestBody User user) {

        int code = userService.update(user);
        switch (code) {
            case 0:
                return Result.success("修改成功");
            case 1:
                return Result.error("邮箱已存在");
            case 2:
                return Result.error("手机号码格式错误");
            default:
                return Result.error("其它错误");
        }
    }

    // 删除用户
    @DeleteMapping("/delete/{username}")
    public Result delete(@PathVariable String username) {
        System.out.println(username);
        int code = userService.delete(username);
        if (code == 0) {
            return Result.success("删除成功");
        } else {
            return Result.error("用户不存在");
        }
    }

}
