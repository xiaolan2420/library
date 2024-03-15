package com.example.mapper;

import com.example.enums.NotificationType;
import com.example.pojo.Feedback;
import com.example.pojo.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalTime;
import java.util.List;

@Mapper
public interface NotificationMapper {

    // 查个人消息个公告
    @Select("SELECT * FROM notifications WHERE user_id = #{userId} OR notification_type = #{notificationType}")
    List<Notification> findByUserId(int userId, NotificationType notificationType);

    // 插入公告
    @Insert("INSERT INTO notifications (user_id, message, notification_type, create_time) " +
            "VALUES(#{userId}, #{message}, #{notificationType}, #{createTime})")
    void add(Integer userId, String message, NotificationType notificationType, LocalTime createTime);

    // 查所有公告
    @Select("SELECT * FROM notifications WHERE notification_type = #{notificationType}")
    List<Notification> find(NotificationType notificationType);

    @Update("UPDATE notifications SET is_read = 1 WHERE notification_id = #{notificationId}")
    void updateBatch(Notification notification);
}
