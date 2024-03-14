package com.example.service;

import com.example.pojo.Feedback;

import java.util.List;

public interface FeedbackService {

    void addUserFeedback(String feedbackText,int userId);

    void addSeatReview(String feedbackText, String seatNumber, Integer floor);

    List<Feedback> findAll();

    List<Feedback> findBySeatId(String seatNumber, Integer floor);

    List<Feedback> findByUseId(int userId);
}
