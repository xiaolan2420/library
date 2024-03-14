package com.example.mapper;

import com.example.pojo.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedbackMapper {

    @Insert("INSERT INTO feedbacks (feedback_text, feedback_type, user_id) VALUES (#{feedbackText}, #{feedbackType}, #{userId})")
    void addUserFeedback(String feedbackText, String feedbackType, int userId);


    @Insert("INSERT INTO feedbacks (feedback_text, feedback_type, seat_id) VALUES (#{feedbackText}, #{feedbackType}, #{seatId})")
    void addSeatReview(String feedbackText, String feedbackType, int seatId);

    @Select("SELECT * FROM feedbacks")
    List<Feedback> findAll();

    @Select("SELECT * FROM feedbacks WHERE seat_id = #{seatId}")
    List<Feedback> findBySeatId(int seatId);

    @Select("SELECT * FROM feedbacks WHERE user_id = #{userId}")
    List<Feedback> findByUseId(int userId);
}
