package com.inf244.zocean;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DateSelection extends AppCompatActivity {

    private Button selectCheckInButton, selectCheckOutButton, confirmButton;
    private EditText checkInDateText, checkOutDateText;

    private Calendar checkInCalendar;
    private Calendar checkOutCalendar;

    private String roomType;
    private int roomPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFF0A1929);

        roomType = getIntent().getStringExtra("ROOM_TYPE");
        roomPrice = getIntent().getIntExtra("ROOM_PRICE", 0);

        selectCheckInButton = findViewById(R.id.selectCheckInButton);
        selectCheckOutButton = findViewById(R.id.selectCheckOutButton);
        confirmButton = findViewById(R.id.confirmButton);

        checkInDateText = findViewById(R.id.checkInDateText);
        checkOutDateText = findViewById(R.id.checkOutDateText);

        selectCheckInButton.setOnClickListener(v -> showDatePicker(true));
        selectCheckOutButton.setOnClickListener(v -> showDatePicker(false));
        confirmButton.setOnClickListener(v -> handleConfirmation());
    }

    private void showDatePicker(boolean isCheckIn) {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(y, m, d, 0, 0, 0);
                    selectedDate.set(Calendar.MILLISECOND, 0);

                    String dateText = y + "-" + String.format("%02d", (m + 1)) + "-" + String.format("%02d", d);

                    if (isCheckIn) {
                        checkInCalendar = selectedDate;
                        checkInDateText.setText(dateText);
                    } else {
                        checkOutCalendar = selectedDate;
                        checkOutDateText.setText(dateText);
                    }
                },
                year, month, day
        );

        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();
    }

    private void handleConfirmation() {
        if (checkInCalendar == null || checkOutCalendar == null) {
            Toast.makeText(this, "Please select both check-in and check-out dates", Toast.LENGTH_SHORT).show();
            return;
        }

        long diffMillis = checkOutCalendar.getTimeInMillis() - checkInCalendar.getTimeInMillis();
        long nights = diffMillis / (1000 * 60 * 60 * 24);

        if (nights <= 0) {
            Toast.makeText(this, "Check-out must be after check-in", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nights > 3) {
            Toast.makeText(this, "Maximum 3 nights allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalPrice = (int) (nights * roomPrice);

        Intent intent = new Intent(this, PaymentSummaryActivity.class);

        intent.putExtra("ROOM_TYPE", roomType);
        intent.putExtra("NIGHTS", (int) nights);
        intent.putExtra("TOTAL_PRICE", totalPrice);
        intent.putExtra("CHECK_IN", checkInDateText.getText().toString());
        intent.putExtra("CHECK_OUT", checkOutDateText.getText().toString());

        startActivity(intent);
    }
}