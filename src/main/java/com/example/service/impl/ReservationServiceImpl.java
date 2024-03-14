package com.example.service.impl;

import com.example.mapper.FeedbackMapper;
import com.example.mapper.ReservationMapper;
import com.example.mapper.SeatMapper;
import com.example.pojo.Reservation;
import com.example.pojo.Seat;
import com.example.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private SeatMapper seatMapper;
    @Autowired
    private ReservationMapper reservationMapper;

    // 判断是否超过最大预约时间，超过为true
    public boolean time(LocalTime startTime, LocalTime endTime, Integer seatId) {
        Seat seat = seatMapper.findBySeatID(seatId);
        Duration between = Duration.between(startTime, endTime);
        long hours = (long) Math.ceil(between.toHours());
        if (hours > seat.getMaxReservationTime()) {
            return true; //超过
        } else {
            return false;  //未超过
        }
    }

    // 判断是否被预约，已经被预约为true
    public boolean isReserved(Integer seatId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime){
        List<Reservation> reservations = reservationMapper.findBySeatID(seatId);

        boolean isReserved = reservations.stream()
                .anyMatch(reservation -> reservation.getReservationDate().equals(reservationDate)
                        && !reservation.getIsCancelled()
                        && reservation.getStartTime().isBefore(endTime)
                        && reservation.getEndTime().isAfter(startTime));

        return isReserved;
    }

    // 查询所有预约
    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = reservationMapper.findAll();
        return reservations;
    }

    // 查询用户预约
    @Override
    public List<Reservation> findByUserId(int userId) {
        List<Reservation> reservations = reservationMapper.findByUserId(userId);
        return reservations;
    }

    // 新建预约
    @Override
    public int reserveSeat(Integer userId, String seatNumber, Integer floor, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) {
        Seat seat = seatMapper.findBySeatNumberAndFloor(seatNumber, floor);
        Integer seatId = seat.getSeatId();
        if (time(startTime, endTime, seatId)) {
            // 超过最大预约时间
            return 1;
        }
        if (!seat.getAvailability()) {
            // 座位不可用
            return 2;
        }
        if (isReserved(seatId, reservationDate, startTime,endTime)) {
            // 座位已被预约
            return 3;
        }
        reservationMapper.reserveSeat(userId, seatId, reservationDate, startTime, endTime);
        return 0; // 预约成功
    }

    // 取消预约
    @Override
    public int cancel(Integer userId, String seatNumber, Integer floor, LocalDate reservationDate, LocalTime startTime) {
        Seat seat = seatMapper.findBySeatNumberAndFloor(seatNumber, floor);
        Integer seatId = seat.getSeatId();
        Reservation reservation = reservationMapper.find(userId, seatId, reservationDate, startTime);
        Integer reservationId = reservation.getReservationId();
        reservationMapper.cancel(reservationId);
        return 0;
    }

    @Override
    public Reservation findBySeatID(String seatNumber, Integer floor) {
        Seat seat = seatMapper.findBySeatNumberAndFloor(seatNumber, floor);
        Integer seatId = seat.getSeatId();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        List<Reservation> reservations = reservationMapper.findBySeatID(seatId);

        // 过滤出当前日期和时间内的预约
        Optional<Reservation> currentReservation = reservations.stream()
                .filter(reservation -> reservation.getReservationDate().equals(date)
                        && reservation.getStartTime().isBefore(time)
                        && reservation.getEndTime().isAfter(time))
                .findFirst();

        return currentReservation.orElse(null);
    }


}
