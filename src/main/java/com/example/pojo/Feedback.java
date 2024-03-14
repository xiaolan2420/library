package com.example.pojo;

import com.example.enums.FeedbackType;
import com.example.enums.UserRole;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Feedback {

    // 反馈的相关属性和方法
    private Integer feedbackId; // 反馈ID
    private Integer userId; // 用户ID
    private Integer seatId; // 座位ID
    private FeedbackType feedbackType; // 反馈类型
    private String feedbackText; // 反馈内容

}
