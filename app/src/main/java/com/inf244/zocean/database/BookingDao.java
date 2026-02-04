package com.inf244.zocean.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.inf244.zocean.model.Booking;

import java.util.List;

@Dao
public interface BookingDao {
    @Insert
    long insert(Booking booking);

    @Update
    void update(Booking booking);

    @Delete
    void delete(Booking booking);

    @Query("SELECT * FROM bookings ORDER BY bookingId DESC")
    List<Booking> getAllBookings();

    @Query("SELECT * FROM bookings WHERE bookingId = :id")
    Booking getBookingById(int id);

    @Query("SELECT * FROM bookings WHERE roomId = :roomId")
    List<Booking> getBookingsByRoomId(int roomId);
}