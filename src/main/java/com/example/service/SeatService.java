package com.example.service;

import com.example.enums.SeatType;
import com.example.pojo.Seat;
import com.example.pojo.User;

import java.util.List;

public interface SeatService {

    Seat findBySeatNumberAndFloor(String seatNumber, Integer floor);

    int add(String seatNumber, Integer floor, SeatType seatType);

    int delete(String seatNumber, Integer floor);

    List<Seat> findAll(int pageNum, int pageSize);

    int update(Seat seat);
}
