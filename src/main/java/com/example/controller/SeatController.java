package com.example.controller;

import com.example.enums.SeatType;
import com.example.enums.UserRole;
import com.example.pojo.Result;
import com.example.pojo.Seat;
import com.example.pojo.User;
import com.example.pojo.UserDTO;
import com.example.service.SeatService;
import com.example.service.UserService;
import com.example.utils.ThreadLocalUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;
    @Autowired
    private UserService userService;

    // 添加座位
    @PostMapping("/add")
    public Result add(String seatNumber, Integer floor, SeatType seatType) {

        int code = seatService.add(seatNumber, floor, seatType);
        if (code == 0) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    // 获取座位列表
    @GetMapping("/list")
    public Result list(int pageNum, int pageSize) {
        List<Seat> seats = seatService.findAll(pageNum, pageSize);
        // 返回一个包含用户列表的Result对象
        if (seats != null) {
            return Result.success(seats);
        } else {
            return Result.error("用户列表为空");
        }
    }


    // 删除座位
    @DeleteMapping("/delete")
    public Result delete(String seatNumber, Integer floor) {

        int code = seatService.delete(seatNumber, floor);
        if (code == 0) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    // 更新座位信息
    @PutMapping("/update")
    public Result update(@RequestBody Seat seat) {

        int code = seatService.update(seat);
        if (code == 0) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    // 查询座位信息
    @GetMapping("/retrieve")
    public Result retrieve(String seatNumber, Integer floor) {
        // 根据用户名查询用户信息
        Seat seat = seatService.findBySeatNumberAndFloor(seatNumber, floor);
        // 返回一个包含用户信息的Result对象
        if (seat != null) {
            return Result.success(seat);
        } else {
            return Result.error("座位不存在");
        }
    }
}
