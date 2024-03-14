package com.example.service.impl;

import com.example.enums.SeatType;
import com.example.mapper.SeatMapper;
import com.example.pojo.Seat;
import com.example.pojo.User;
import com.example.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public Seat findBySeatNumberAndFloor(String seatNumber, Integer floor) {
        return seatMapper.findBySeatNumberAndFloor(seatNumber,floor);
    }


    @Override
    public int add(String seatNumber,Integer floor, SeatType seatType) {

        if(seatMapper.findBySeatNumberAndFloor(seatNumber,floor)== null){
            seatMapper.add(seatNumber,floor,seatType);
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int delete(String seatNumber, Integer floor) {
        if(seatMapper.findBySeatNumberAndFloor(seatNumber,floor)!= null){
            seatMapper.delete(seatNumber,floor);
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public List<Seat> findAll() {
        List<Seat> seats = seatMapper.findAll();
        return seats;
    }

    @Override
    public int update(Seat seat) {
        if (seatMapper.findBySeatNumberAndFloor(seat.getSeatNumber(),seat.getFloor()) != null) {
            seatMapper.update(seat);
            return 0;
        }
        return 1;
    }
}
