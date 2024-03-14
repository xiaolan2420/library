package com.example.pojo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Reservation {
    private Integer reservationId;
    private Integer userId;
    private Integer seatId;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isCancelled;

}

