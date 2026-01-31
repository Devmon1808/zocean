package com.inf244.zocean;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    EditText et_usernameInput, et_inputBookingRoomId,
            et_inputCheckIn, et_inputCheckOut, et_inputTotalPrice;

    Button b_AddBooking, b_edit, b_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_bookings);

        et_usernameInput = findViewById(R.id.et_usernameInput);
        et_inputBookingRoomId = findViewById(R.id.et_inputBookingRoomId);
        et_inputCheckIn = findViewById(R.id.et_inputCheckIn);
        et_inputCheckOut = findViewById(R.id.et_inputCheckOut);
        et_inputTotalPrice = findViewById(R.id.et_inputTotalPrice);

        b_AddBooking = findViewById(R.id.b_AddBooking);
        b_edit = findViewById(R.id.b_edit);
        b_delete = findViewById(R.id.b_delete);

        b_AddBooking.setOnClickListener(v -> {
            Intent intent = new Intent(BookingActivity.this, RoomAdminPanel.class);
            startActivity(intent);
        });
    }
}
