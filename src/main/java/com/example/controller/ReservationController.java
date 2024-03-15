package com.example.controller;

import com.example.enums.SeatType;
import com.example.pojo.Reservation;
import com.example.pojo.Result;
import com.example.pojo.Seat;
import com.example.service.ReservationService;
import com.example.service.SeatService;
import com.example.service.UserService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private SeatService seatService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    // 预约
    @PostMapping("/reserve")
    public Result reserveSeat(String seatNumber, Integer floor, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) {

        // 获取当前登录的用户ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        // 添加座位预约信息
        int code = reservationService.reserveSeat(userId, seatNumber, floor, reservationDate, startTime, endTime);
        switch (code) {
            case 0:
                return Result.success("预约成功");
            case 1:
                return Result.error("超过最大预约时间");
            case 2:
                return Result.error("座位损坏");
            case 3:
                return Result.error("座位已被预约");
            default:
                return Result.error("未知错误");
        }
    }

    // 获取预约列表
    @GetMapping("/list")
    public Result list(int pageNum, int pageSize) {

        List<Reservation> reservations = reservationService.findAll(pageNum, pageSize);
        // 返回一个包含用户列表的Result对象
        if (reservations != null) {
            return Result.success(reservations);
        } else {
            return Result.error("预约列表为空");
        }
    }

    // 获取用户自己的预约列表
    @GetMapping("/reservationInfo")
    public Result reservationInfo(int pageNum,int pageSize) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Reservation> reservations = reservationService.findByUserId(userId, pageNum, pageSize);
        // 返回一个包含用户列表的Result对象
        if (reservations != null) {
            return Result.success(reservations);
        } else {
            return Result.error("预约列表为空");
        }
    }

    // 取消预约
    @PutMapping("/cancel")
    public Result cancel(String seatNumber, Integer floor, LocalDate reservationDate, LocalTime startTime) {

        // 获取当前登录的用户ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        int code = reservationService.cancel(userId, seatNumber, floor, reservationDate, startTime);
        if (code == 0) {
            return Result.success("取消成功");
        } else {
            return Result.error("未知错误");
        }
    }


    // 查询座位当前预约消息
    @GetMapping("/retrieve")
    public Result retrieve(String seatNumber, Integer floor) {
        // 根据用户名查询用户信息
        Reservation reservation = reservationService.findBySeatID(seatNumber, floor);
        // 返回一个包含用户信息的Result对象
        if (reservation != null) {
            return Result.success(reservation);
        } else {
            return Result.error("当前座位没有预约");
        }
    }
}
