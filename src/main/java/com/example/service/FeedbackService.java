package com.example.service;

import com.example.pojo.Feedback;

import java.util.List;

public interface FeedbackService {

    void addUserFeedback(String feedbackText,int userId);

    void addSeatReview(String feedbackText, String seatNumber, Integer floor);

    List<Feedback> findAll(int pageNum,int pageSize);

    List<Feedback> findBySeatId(String seatNumber, Integer floor,int pageNum,int pageSize);

    List<Feedback> findByUseId(int userId,int pageNum,int pageSize);
}
