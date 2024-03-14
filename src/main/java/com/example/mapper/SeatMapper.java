package com.example.mapper;

import com.example.enums.SeatType;
import com.example.pojo.Seat;
import com.mysql.cj.protocol.a.BinaryRowFactory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeatMapper {
    @Select("SELECT * FROM seats WHERE seat_number = #{seatNumber} AND floor = #{floor}")
    Seat findBySeatNumberAndFloor(String seatNumber, Integer floor);

    @Select("SELECT * FROM seats WHERE seat_id = #{seatID}")
    Seat findBySeatID(int seatID);

    @Update("UPDATE seats SET availability = #{availability} WHERE seat_id = #{seatId}")
    void updateSeatAvailability(int seatId,boolean availability);

    @Insert("INSERT INTO seats (seat_number, floor, seat_type) VALUES (#{seatNumber}, #{floor}, #{seatType})")
    void add(String seatNumber, Integer floor, SeatType seatType);

    @Delete("DELETE FROM seats WHERE seat_number = #{seatNumber} AND floor = #{floor}")
    void delete(String seatNumber, Integer floor);

    @Select("SELECT * FROM seats")
    List<Seat> findAll();

    @Update("UPDATE seats SET seat_type = #{seatType}, availability = #{availability}, max_reservation_time= #{maxReservationTime} WHERE seat_number = #{seatNumber} AND floor = #{floor}")
    void update(Seat seat);
}
