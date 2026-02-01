package com.inf244.zocean.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.inf244.zocean.model.Booking;
import com.inf244.zocean.model.Guest;

@Database(entities = {Guest.class, com.inf244.zocean.model.Room.class, Booking.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract GuestDao guestDao();
    public abstract RoomDao roomDao();
    public abstract BookingDao bookingDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hotel_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}