package com.example.controller;

import com.example.pojo.Feedback;
import com.example.pojo.Notification;
import com.example.pojo.Result;
import com.example.service.FeedbackService;
import com.example.service.NotificationService;
import com.example.service.SeatService;
import com.example.service.UserService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    // 创建公告
    @PostMapping("/createNotification")
    public Result createNotification(String message) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        int code =notificationService.createNotification(userId, message);
        if(code==1){
            return  Result.success(code);
        }else {
            return Result.error("创建失败");
        }

    }

    // 展示所有公告
    @GetMapping("/list")
    public Result list(int pageNum,int pageSize) {
        List<Notification> notifications = notificationService.list(pageNum,pageSize);
        return  Result.success(notifications);
    }

    // 查询个人消息和公告
    @GetMapping("/getNotification")
    public Result getNotifications(int pageNum,int pageSize) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Notification> notifications = notificationService.getNotifications(userId,pageNum,pageSize);
        return  Result.success(notifications);
    }

    // 查询未读消息
    @GetMapping("/unread")
    public Result getUnreadNotifications(int pageNum,int pageSize) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Notification> notifications = notificationService.getUnreadNotifications(userId,pageNum,pageSize);
        return  Result.success(notifications);
    }

    // 一键已读
    @GetMapping("/readAll")
    public Result readAllNotifications() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        notificationService.readAllNotifications(userId);
        return  Result.success();
    }

    // 预约成功通知
    @PostMapping("/reservationSuccess")
    public Result reservationSuccess(String seatNumber, Integer floor) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        notificationService.reservationSuccess(userId,seatNumber,floor);
        return  Result.success("预约成功通知发送成功");
    }

    // 取消预约成功通知
    @PostMapping("/cancelReservationSuccess")
    public Result cancelReservationSuccess(String seatNumber, Integer floor) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        notificationService.cancelReservationSuccess(userId,seatNumber,floor);
        return  Result.success("取消预约通知发送成功");
    }

}
