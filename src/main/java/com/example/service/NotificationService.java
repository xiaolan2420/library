package com.example.service;

import com.example.pojo.Feedback;
import com.example.pojo.Notification;

import java.util.List;

public interface NotificationService {

    int createNotification(int userId, String message);

    List<Notification> getNotifications(int userId);

    List<Notification> list();

    void reservationSuccess(Integer userId, String seatNumber, Integer floor);

    void cancelReservationSuccess(Integer userId, String seatNumber, Integer floor);

    List<Notification> getUnreadNotifications(Integer userId);

    void readAllNotifications(Integer userId);
}
