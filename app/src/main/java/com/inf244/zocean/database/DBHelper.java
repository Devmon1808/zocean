package com.inf244.zocean.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hotel.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE hotel (" +
                "hotelId TEXT PRIMARY KEY," +
                "hotelName TEXT," +
                "hotelLocation TEXT," +
                "hotelHotline TEXT)");

        db.execSQL("CREATE TABLE room (" +
                "roomId TEXT PRIMARY KEY," +
                "hotelId TEXT," +
                "roomNumber TEXT," +
                "roomType TEXT," +
                "roomStatus TEXT)");

        db.execSQL("CREATE TABLE guest (" +
                "guestId TEXT PRIMARY KEY," +
                "fullName TEXT," +
                "birthDate DATE," +
                "email TEXT," +
                "contactNumber INTEGER," +
                "address TEXT)");

        db.execSQL("CREATE TABLE booking (" +
                "bookingId TEXT PRIMARY KEY," +
                "guestId TEXT," +
                "roomId TEXT," +
                "checkInDate TEXT," +
                "checkOutDate TEXT," +
                "guestAmount INTEGER," +
                "remainingBalance REAL)");

        db.execSQL("CREATE TABLE payment (" +
                "paymentId TEXT PRIMARY KEY," +
                "bookingId TEXT," +
                "paymentDate DATE," +
                "totalAmount REAL," +
                "paymentMethod TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS payment");
        db.execSQL("DROP TABLE IF EXISTS booking");
        db.execSQL("DROP TABLE IF EXISTS guest");
        db.execSQL("DROP TABLE IF EXISTS room");
        db.execSQL("DROP TABLE IF EXISTS hotel");
        onCreate(db);
    }

    // ======================
    // INSERT METHODS
    // ======================

    public void insertHotel(String name, String address) {
        getWritableDatabase().execSQL(
                "INSERT INTO hotel (name, address) VALUES (?,?)",
                new Object[]{name, address}
        );
    }

    public void insertRoom(int hotelId, String roomNumber, String type, double price) {
        getWritableDatabase().execSQL(
                "INSERT INTO room (hotel_id, room_number, type, price) VALUES (?,?,?,?)",
                new Object[]{hotelId, roomNumber, type, price}
        );
    }

    public void insertGuest(String name, String email, String phone) {
        getWritableDatabase().execSQL(
                "INSERT INTO guest (name, email, phone) VALUES (?,?,?)",
                new Object[]{name, email, phone}
        );
    }

    public void insertBooking(int guestId, int roomId, String checkIn, String checkOut) {
        getWritableDatabase().execSQL(
                "INSERT INTO booking (guest_id, room_id, check_in, check_out) VALUES (?,?,?,?)",
                new Object[]{guestId, roomId, checkIn, checkOut}
        );
    }

    public void insertPayment(int bookingId, double amount, String method, String date) {
        getWritableDatabase().execSQL(
                "INSERT INTO payment (booking_id, amount, method, date) VALUES (?,?,?,?)",
                new Object[]{bookingId, amount, method, date}
        );
    }

    // ======================
    // CSV IMPORTS (YOUR STYLE)
    // ======================

    public void importHotelCSV(Context context, int csvResId) {
        try {
            InputStream is = context.getResources().openRawResource(csvResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                insertHotel(data[0], data[1]);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importRoomCSV(Context context, int csvResId) {
        try {
            InputStream is = context.getResources().openRawResource(csvResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");
                insertRoom(Integer.parseInt(d[0]), d[1], d[2], Double.parseDouble(d[3]));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importGuestCSV(Context context, int csvResId) {
        try {
            InputStream is = context.getResources().openRawResource(csvResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");
                insertGuest(d[0], d[1], d[2]);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importBookingCSV(Context context, int csvResId) {
        try {
            InputStream is = context.getResources().openRawResource(csvResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");
                insertBooking(Integer.parseInt(d[0]), Integer.parseInt(d[1]), d[2], d[3]);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importPaymentCSV(Context context, int csvResId) {
        try {
            InputStream is = context.getResources().openRawResource(csvResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");
                insertPayment(Integer.parseInt(d[0]), Double.parseDouble(d[1]), d[2], d[3]);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ======================
    // READ (Example)
    // ======================

    public Cursor getAllHotels() {
        return getReadableDatabase().rawQuery("SELECT * FROM hotel", null);
    }
}