package com.example.service.impl;

import com.example.enums.FeedbackType;
import com.example.mapper.FeedbackMapper;
import com.example.mapper.SeatMapper;
import com.example.pojo.Feedback;
import com.example.pojo.Seat;
import com.example.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private SeatMapper seatMapper;

    @Override
    public void addUserFeedback(String feedbackText, int userId) {
        String feedbackType="user_feedback";
        feedbackMapper.addUserFeedback(feedbackText,feedbackType,userId);
    }

    @Override
    public void addSeatReview(String feedbackText, String seatNumber, Integer floor) {
        Seat seat=seatMapper.findBySeatNumberAndFloor(seatNumber,floor);
        Integer seatId=seat.getSeatId();
        String feedbackType="seat_review";
        feedbackMapper.addSeatReview(feedbackText,feedbackType,seatId);
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = feedbackMapper.findAll();
        return feedbacks;
    }

    @Override
    public List<Feedback> findBySeatId(String seatNumber, Integer floor) {
        Seat seat=seatMapper.findBySeatNumberAndFloor(seatNumber,floor);
        Integer seatId=seat.getSeatId();
        List<Feedback> feedbacks = feedbackMapper.findBySeatId(seatId);
        return feedbacks;
    }

    @Override
    public List<Feedback> findByUseId(int userId) {
        List<Feedback> feedbacks = feedbackMapper.findByUseId(userId);
        return feedbacks;
    }

}
