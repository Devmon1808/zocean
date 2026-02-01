package com.inf244.zocean.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.inf244.zocean.model.Guest;

import java.util.List;

@Dao
public interface GuestDao {
    @Insert
    void insert(Guest guest);

    @Update
    void update(Guest guest);

    @Delete
    void delete(Guest guest);

    @Query("SELECT * FROM guests ORDER BY guestId DESC")
    List<Guest> getAllGuests();

    @Query("SELECT * FROM guests WHERE guestId = :id")
    Guest getGuestById(int id);
}
