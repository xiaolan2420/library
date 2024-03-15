package com.example.service.impl;

import com.example.enums.NotificationType;
import com.example.enums.UserRole;
import com.example.mapper.FeedbackMapper;
import com.example.mapper.NotificationMapper;
import com.example.mapper.SeatMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Feedback;
import com.example.pojo.Notification;
import com.example.pojo.Seat;
import com.example.pojo.User;
import com.example.service.FeedbackService;
import com.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    // 创建公告
    @Override
    public int createNotification(int userId, String message) {

        User user = userMapper.findByUserId(userId);
        LocalTime createTime = LocalTime.now();
        NotificationType notificationType = NotificationType.announcement;
        if (user.getRole() == UserRole.admin) {
            notificationMapper.add(userId, message, notificationType, createTime);
            return 1;
        }
        return 0;
    }

    // 个人消息和公告
    @Override
    public List<Notification> getNotifications(int userId) {
        NotificationType notificationType = NotificationType.announcement;
        List<Notification> notifications = notificationMapper.findByUserId(userId, notificationType);
        return notifications;
    }

    // 所有公告
    @Override
    public List<Notification> list() {
        NotificationType notificationType = NotificationType.announcement;
        List<Notification> notifications = notificationMapper.find(notificationType);
        return notifications;
    }

    // 预约成功通知
    @Override
    public void reservationSuccess(Integer userId, String seatNumber, Integer floor) {
        LocalTime createTime = LocalTime.now();
        NotificationType notificationType = NotificationType.reservation_reminder;
        String message = "您的座位 " + seatNumber + " 在楼层 " + floor + " 已经预约成功，请在规定时间到指定位置就座。";
        notificationMapper.add(userId, message, notificationType, createTime);
    }

    // 取消预约通知
    @Override
    public void cancelReservationSuccess(Integer userId, String seatNumber, Integer floor) {
        LocalTime createTime = LocalTime.now();
        NotificationType notificationType = NotificationType.reservation_change;
        String message = "您的座位 " + seatNumber + " 在楼层 " + floor + " 已经取消成功。";
        notificationMapper.add(userId, message, notificationType, createTime);
    }

    //未读消息
    @Override
    public List<Notification> getUnreadNotifications(Integer userId) {
        User user = userMapper.findByUserId(userId);
        int maxReadNotification=user.getReadNotification();
        NotificationType notificationType = NotificationType.announcement;
        List<Notification> notifications = notificationMapper.findByUserId(userId, notificationType);
        // 过滤掉已读的通知
        List<Notification> unreadNotifications = notifications.stream()
                .filter(notification -> notification.getNotificationId() > maxReadNotification)
                .collect(Collectors.toList());
        return unreadNotifications;
    }

    // 一件已读
    @Override
    public void readAllNotifications(Integer userId) {
        User user = userMapper.findByUserId(userId);
        NotificationType notificationType = NotificationType.announcement;
        List<Notification> notifications = notificationMapper.findByUserId(userId, notificationType);
        int maxReadNotification = notifications.stream()
                .mapToInt(Notification::getNotificationId)
                .max()
                .orElse(0);

        for(Notification notification: notifications){
            notificationMapper.updateBatch(notification);
        }

        user.setReadNotification(maxReadNotification);
        userMapper.updateReadNotification(user);
    }



}
