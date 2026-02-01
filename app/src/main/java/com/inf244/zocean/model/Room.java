package com.inf244.zocean.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rooms")
public class Room {
    @PrimaryKey(autoGenerate = true)
    private int roomId;
    private String roomType;
    private double price;
    private String status;

    public Room(String roomType, double price, String status) {
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}