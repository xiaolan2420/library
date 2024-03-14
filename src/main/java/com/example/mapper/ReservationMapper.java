package com.example.mapper;

import com.example.pojo.Reservation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface ReservationMapper {

    // 查询一个预约消息
    @Select("SELECT * FROM reservations WHERE user_id = #{userId} AND seat_id = #{seatId} AND reservation_date = #{reservationDate} AND start_time = #{startTime}")
    Reservation find(Integer userId, Integer seatId, LocalDate reservationDate, LocalTime startTime);

    // 查询预定消息
    @Select("SELECT * FROM reservations")
    List<Reservation> findAll();

    // 根据用户ID查询预定信息
    @Select("SELECT * FROM reservations WHERE user_id = #{userId}")
    List<Reservation> findByUserId(int userId);

    // 根据座位ID查询未取消的预定信息
    @Select("SELECT * FROM reservations WHERE seat_id = #{seatId}")
    List<Reservation> findBySeatID(Integer seatId);

    // 插入预定信息
    @Insert("INSERT INTO reservations (user_id, seat_id, reservation_date, start_time, end_time) " +
            "VALUES (#{userId}, #{seatId}, #{reservationDate}, #{startTime}, #{endTime})")
    void reserveSeat(Integer userId, Integer seatId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime);

    // 取消预定
    @Update("UPDATE reservations SET is_cancelled = 1 WHERE reservation_id = #{reservationId}")
    void cancel(Integer reservationId);

}
