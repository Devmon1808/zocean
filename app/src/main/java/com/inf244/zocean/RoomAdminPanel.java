package com.inf244.zocean;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RoomAdminPanel extends AppCompatActivity {

    EditText inputRoomId, inputRoomType, inputRoomPrice, inputRoomStatus;

    Button b_AddRoom, b_update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_bookings);

        inputRoomId = findViewById(R.id.inputRoomId);
        inputRoomType = findViewById(R.id.inputRoomType);
        inputRoomPrice = findViewById(R.id.inputRoomPrice);
        inputRoomStatus = findViewById(R.id.inputRoomStatus);

        b_AddRoom = findViewById(R.id.b_AddRoom);
        b_update = findViewById(R.id.b_update);
        delete = findViewById(R.id.delete);

        b_AddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(RoomAdminPanel.this, RoomAdminPanel.class);
            startActivity(intent);
        });
    }
}
