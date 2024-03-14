package com.example.pojo;

import com.example.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private String username; // 用户名
    private UserRole role; // 用户角色
    private String email; // 邮箱
    private String phoneNumber; // 手机号码

}
