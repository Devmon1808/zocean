package com.inf244.zocean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.inf244.zocean.database.AppDatabase;
import com.inf244.zocean.model.Booking;
import com.inf244.zocean.model.Guest;
import com.inf244.zocean.model.Room;

public class PaymentSummaryActivity extends AppCompatActivity {

    private TextView roomTypeText, nightsText, totalPriceText;
    private RadioGroup paymentMethodGroup;
    private Button confirmPaymentButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFF0A1929);

        db = AppDatabase.getInstance(this);

        roomTypeText = findViewById(R.id.roomTypeText);
        nightsText = findViewById(R.id.nightsText);
        totalPriceText = findViewById(R.id.totalPriceText);
        paymentMethodGroup = findViewById(R.id.paymentMethodGroup);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);

        String roomType = getIntent().getStringExtra("ROOM_TYPE");
        String checkInDate = getIntent().getStringExtra("CHECK_IN_DATE");
        String checkOutDate = getIntent().getStringExtra("CHECK_OUT_DATE");
        int nights = getIntent().getIntExtra("NIGHTS", 1);
        int totalPrice = getIntent().getIntExtra("TOTAL_PRICE", 0);

        roomTypeText.setText(roomType);
        nightsText.setText(nights + " night(s)");
        totalPriceText.setText("₱" + String.format("%,d", totalPrice));

        confirmPaymentButton.setOnClickListener(v -> {
            int selectedPaymentId = paymentMethodGroup.getCheckedRadioButtonId();

            if (selectedPaymentId == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            String paymentMethod;
            if (selectedPaymentId == R.id.onlinePaymentRadio) {
                paymentMethod = "Online Payment (Full Amount)";
            } else {
                paymentMethod = "Cash Payment (50% Deposit)";
            }

            saveBookingToDatabase(roomType, checkInDate, checkOutDate, totalPrice);

            Toast.makeText(this, "Booking confirmed!\n" +
                            roomType + " for " + nights + " night(s)\n" +
                            "Payment: " + paymentMethod,
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(PaymentSummaryActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void saveBookingToDatabase(String roomType, String checkIn, String checkOut, double totalPrice) {
        try {
            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            String guestName = prefs.getString("guestName", "Guest");
            String guestEmail = prefs.getString("guestEmail", "guest@gmail.com");
            String guestPhone = prefs.getString("guestPhone", "N/A");

            Guest existingGuest = db.guestDao().getGuestByEmail(guestEmail);
            int guestId;

            if (existingGuest != null) {
                guestId = existingGuest.getGuestId();
                Log.d("Payment", "Using existing guest ID: " + guestId);
            } else {
                Guest newGuest = new Guest(guestName, guestEmail, guestPhone);
                guestId = (int) db.guestDao().insert(newGuest);
                Log.d("Payment", "Created new guest ID: " + guestId);
            }

            Room existingRoom = db.roomDao().getRoomByType(roomType);
            int roomId;

            if (existingRoom != null) {
                roomId = existingRoom.getRoomId();
                Log.d("Payment", "Using existing room ID: " + roomId);
            } else {
                double roomPrice = totalPrice / (calculateNights(checkIn, checkOut));
                Room newRoom = new Room(roomType, roomPrice, "Available");
                roomId = (int) db.roomDao().insert(newRoom);
                Log.d("Payment", "Created new room ID: " + roomId);
            }

            Booking booking = new Booking(guestId, roomId, checkIn, checkOut, totalPrice);
            long bookingId = db.bookingDao().insert(booking);
            Log.d("Payment", "Created booking ID: " + bookingId);

            db.roomDao().updateRoomStatus(roomId, "Occupied");
            Log.d("Payment", "Room " + roomId + " marked as Occupied");

            Toast.makeText(this, "Booking saved to database!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("Payment", "Error saving booking: " + e.getMessage());
            Toast.makeText(this, "Error saving booking: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private int calculateNights(String checkIn, String checkOut) {
        return 1;
    }
}
