package com.inf244.zocean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.inf244.zocean.model.Guest;

public class GuestActivity extends AppCompatActivity {

    Button tabGuests, tabRooms, tabBookings;
    EditText inputGuestId, inputGuestName, inputGuestEmail, inputGuestPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_guest);

        tabGuests = findViewById(R.id.tabGuests);
        tabRooms =  findViewById(R.id.tabRooms);
        tabBookings =  findViewById(R.id.tabBookings);

        inputGuestId = findViewById(R.id.inputGuestPhone);
        inputGuestName = findViewById(R.id.inputGuestName);
        inputGuestEmail = findViewById(R.id.inputGuestEmail);
        inputGuestPhone = findViewById(R.id.inputGuestPhone);

        tabGuests.setOnClickListener(v -> {
            Intent intent = new Intent(GuestActivity.this, GuestActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void goToGuest(View view) {
        Intent intent = new Intent(GuestActivity.this, BookingActivity.class);
        startActivity(intent);
        finish();
    }
}
