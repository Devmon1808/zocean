package com.inf244.zocean.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings",
        foreignKeys = {
                @ForeignKey(entity = Guest.class,
                        parentColumns = "guestId",
                        childColumns = "guestId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Room.class,
                        parentColumns = "roomId",
                        childColumns = "roomId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Booking {
    @PrimaryKey(autoGenerate = true)
    private int bookingId;
    private int guestId;
    private int roomId;
    private String checkInDate;
    private String checkOutDate;
    private double totalPrice;

    public Booking(int guestId, int roomId, String checkInDate, String checkOutDate, double totalPrice) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public String getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}