package com.inf244.zocean.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.inf244.zocean.model.Room;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    long insert(Room room);  // Changed from void to long

    @Update
    void update(Room room);

    @Delete
    void delete(Room room);

    @Query("SELECT * FROM rooms ORDER BY roomId DESC")
    List<Room> getAllRooms();

    @Query("SELECT * FROM rooms WHERE roomId = :id")
    Room getRoomById(int id);

    @Query("UPDATE rooms SET status = :status WHERE roomId = :roomId")
    void updateRoomStatus(int roomId, String status);

    @Query("SELECT * FROM rooms WHERE roomType = :roomType LIMIT 1")
    Room getRoomByType(String roomType);
}