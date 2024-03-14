package com.example.pojo;

import com.example.enums.SeatType;
import lombok.Data;

@Data
public class Seat {

    // 座位的唯一标识符
    private Integer seatId;

    // 座位的编号
    private String seatNumber;

    // 座位所在的楼层
    private Integer floor;

    // 座位的类型
    private SeatType seatType;

    // 座位是否可用
    private Boolean availability;

    // 最大预约时间
    private int maxReservationTime;
}
