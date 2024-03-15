package com.example.controller;

import com.example.pojo.Feedback;
import com.example.pojo.Result;
import com.example.pojo.Seat;
import com.example.pojo.User;
import com.example.service.FeedbackService;
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
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private SeatService seatService;
    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackService feedbackService;

    // 用户提交反馈
    @PostMapping("/userFeedback")
    public Result userFeedbackRetrieve(String feedbackText) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        feedbackService.addUserFeedback(feedbackText, userId);
        return Result.success("反馈成功");
    }

    // 用户反馈记录
    @GetMapping("/userFeedbackRetrieve")
    public Result userFeedback(int pageNum,int pageSize) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Feedback> feedbacks = feedbackService.findByUseId(userId,pageNum,pageSize);
        // 返回一个包含用户信息的Result对象
        if (feedbacks != null) {
            return Result.success(feedbacks);
        } else {
            return Result.error("座位评价不存在");
        }
    }

    // 提交座位评价
    @PostMapping("/seatReview")
    public Result seatReview(String feedbackText,String seatNumber, Integer floor) {
        // 根据用户名查询用户信息
        feedbackService.addSeatReview(feedbackText, seatNumber,floor);
        return Result.success("评价成功");
    }

    // 查询座位评价
    @GetMapping("/seatReviewRetrieve")
    public Result seatReviewRetrieve(String seatNumber, Integer floor,int pageNum,int pageSize) {

        List<Feedback> feedbacks = feedbackService.findBySeatId(seatNumber,floor,pageNum,pageSize);
        // 返回一个包含用户信息的Result对象
        if (feedbacks != null) {
            return Result.success(feedbacks);
        } else {
            return Result.error("座位评价不存在");
        }
    }

    // 管理员查看反馈列表
    @GetMapping("/list")
    public Result list(int pageNum,int pageSize) {

        List<Feedback> feedbacks = feedbackService.findAll(pageNum,pageSize);
        // 返回一个包含用户列表的Result对象
        if (feedbacks != null) {
            return Result.success(feedbacks);
        } else {
            return Result.error("反馈列表为空");
        }
    }

}
