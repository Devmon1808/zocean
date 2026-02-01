package com.inf244.zocean.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "guests")
public class Guest {
    @PrimaryKey(autoGenerate = true)
    private int guestId;
    private String guestName;
    private String email;
    private String phoneNumber;

    public Guest(String guestName, String email, String phoneNumber) {
        this.guestName = guestName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}