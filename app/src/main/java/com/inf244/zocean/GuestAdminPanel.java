package com.inf244.zocean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GuestAdminPanel extends AppCompatActivity {

    Button tabGuests, tabRooms, tabBookings, btnAddGuest;
    EditText inputGuestId, inputGuestName, inputGuestEmail, inputGuestPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        btnAddGuest = findViewById(R.id.btnAddGuest);
        tabGuests = findViewById(R.id.tabGuests);
        tabRooms = findViewById(R.id.tabRooms);
        tabBookings = findViewById(R.id.tabBookings);

        inputGuestId = findViewById(R.id.inputGuestId);
        inputGuestName = findViewById(R.id.inputGuestName);
        inputGuestEmail = findViewById(R.id.inputGuestEmail);
        inputGuestPhone = findViewById(R.id.inputGuestPhone);

        btnAddGuest.setOnClickListener(v -> {
            Intent intent = new Intent(GuestAdminPanel.this, RoomAdminPanel.class);
            startActivity(intent);
        });

        tabRooms.setOnClickListener(v -> {
            Intent intent = new Intent(GuestAdminPanel.this, RoomAdminPanel.class);
            startActivity(intent);
        });

        tabBookings.setOnClickListener(v -> {
            Intent intent = new Intent(GuestAdminPanel.this, BookingActivity.class);
            startActivity(intent);
        });
    }

    public void goToGuest(View view) {
        Intent intent = new Intent(GuestAdminPanel.this, RoomAdminPanel.class);
        startActivity(intent);
        finish();
    }
}
