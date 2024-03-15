package com.example.pojo;

import com.example.enums.UserRole;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class User {

    private Integer userId; // 用户ID
    private String username; // 用户名
    private String password; // 密码
    private UserRole role; // 用户角色
    private String email; // 邮箱
    private String phoneNumber; // 手机号码
    private int readNotification;// 已读消息最大编号
    private Timestamp createTime; // 创建时间
    private Timestamp updateTime; // 更新时间

}

