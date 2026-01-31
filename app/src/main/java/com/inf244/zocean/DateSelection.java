package com.inf244.zocean;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DateSelection extends AppCompatActivity {

    Button selectCheckInButton, selectCheckOutButton, confirmButton;
    EditText checkInDateText, checkOutDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        selectCheckInButton = findViewById(R.id.selectCheckInButton);
        selectCheckOutButton = findViewById(R.id.selectCheckOutButton);
        confirmButton = findViewById(R.id.confirmButton);

        checkInDateText = findViewById(R.id.checkInDateText);
        checkOutDateText = findViewById(R.id.checkOutDateText);

        selectCheckInButton.setOnClickListener(v ->
                showDatePicker(checkInDateText));

        selectCheckOutButton.setOnClickListener(v ->
                showDatePicker(checkOutDateText));

        confirmButton.setOnClickListener(v -> {
            Intent intent = new Intent(DateSelection.this, PaymentD1.class);
            intent.putExtra("CHECK_IN", checkInDateText.getText().toString());
            intent.putExtra("CHECK_OUT", checkOutDateText.getText().toString());
            startActivity(intent);
        });
    }

    private void showDatePicker(EditText targetField) {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    String date = y + "-" + (m + 1) + "-" + d;
                    targetField.setText(date);
                },
                year, month, day
        );

        dialog.show();
    }
}
