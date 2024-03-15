package com.example.pojo;

import com.example.enums.FeedbackType;
import com.example.enums.NotificationType;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notification {
    private Integer notificationId; // 通知ID
    private Integer userId; // 用户ID
    private String  message; // 通知主体
    private NotificationType notificationType; // 通知类型
    private boolean  isRead; // 是否已读
    private Timestamp createTime; // 创建时间
}
