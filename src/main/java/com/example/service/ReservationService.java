package com.example.service;

import com.example.pojo.Reservation;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface ReservationService {
    List<Reservation> findAll(int pageNum, int pageSize);

    List<Reservation> findByUserId(int userId, int pageNum, int pageSize);

    int reserveSeat(Integer userId, String seatNumber, Integer floor, LocalDate reservationDate, LocalTime startTime, LocalTime endTime);


    int cancel(Integer userId, String seatNumber, Integer floor, LocalDate reservationDate, LocalTime startTime);


    Reservation findBySeatID(String seatNumber, Integer floor);
}
