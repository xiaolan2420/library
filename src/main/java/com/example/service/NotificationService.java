package com.example.service;

import com.example.pojo.Feedback;
import com.example.pojo.Notification;

import java.util.List;

public interface NotificationService {

    int createNotification(int userId, String message);

    List<Notification> getNotifications(int userId,int pageNum,int pageSize);

    List<Notification> list(int pageNum,int pageSize);

    void reservationSuccess(Integer userId, String seatNumber, Integer floor);

    void cancelReservationSuccess(Integer userId, String seatNumber, Integer floor);

    List<Notification> getUnreadNotifications(Integer userId,int pageNum,int pageSize);

    void readAllNotifications(Integer userId);
}
