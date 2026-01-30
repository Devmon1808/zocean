package com.inf244.zocean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button singleRoomButton, doubleRoomButton, suiteRoomButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        singleRoomButton = findViewById(R.id.singleRoomButton);
        doubleRoomButton = findViewById(R.id.doubleRoomButton);
        suiteRoomButton = findViewById(R.id.suiteRoomButton);

        singleRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReservation("OCEAN SINGLE");
            }
        });

        doubleRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReservation("OCEAN DOUBLE");
            }
        });

        suiteRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReservation("OCEAN SUITE");
            }
        });
    }

    private void handleReservation(String roomType) {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, DateSelectionActivity.class);
            intent.putExtra("ROOM_TYPE", roomType);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("ROOM_TYPE", roomType);
            startActivity(intent);
        }
    }
}
