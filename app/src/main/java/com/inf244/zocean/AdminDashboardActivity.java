package com.inf244.zocean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.inf244.zocean.database.AppDatabase;
import com.inf244.zocean.model.Booking;
import com.inf244.zocean.model.Guest;
import com.inf244.zocean.model.Room;

import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    private AppDatabase db;
    private View guestLayout, roomLayout, bookingLayout;

    private EditText inputGuestId, inputGuestName, inputGuestEmail, inputGuestPhone;
    private LinearLayout guestListContainer;
    private Button btnAddGuest;

    private EditText inputRoomId, inputRoomType, inputRoomPrice, inputRoomStatus;
    private LinearLayout roomListContainer;
    private Button bAddRoom;

    private EditText etUsernameInput, etInputBookingRoomId, etInputCheckIn, etInputCheckOut, etInputTotalPrice;
    private LinearLayout bookingListContainer;
    private Button bAddBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDatabase.getInstance(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        guestLayout = inflater.inflate(R.layout.activity_admin_panel_guest, null);
        roomLayout = inflater.inflate(R.layout.activity_admin_panel_rooms, null);
        bookingLayout = inflater.inflate(R.layout.activity_admin_panel_bookings, null);

        setupSignOutButton(guestLayout);
        setupSignOutButton(roomLayout);
        setupSignOutButton(bookingLayout);

        setContentView(guestLayout);
        initializeGuestViews();
        setupTabButtons();
    }

    private void setupTabButtons() {
        setupGuestTabs();
        setupRoomTabs();
        setupBookingTabs();
    }

    private void setupGuestTabs() {
        guestLayout.findViewById(R.id.tabGuests).setOnClickListener(v -> showGuestPanel());
        guestLayout.findViewById(R.id.tabRooms).setOnClickListener(v -> showRoomPanel());
        guestLayout.findViewById(R.id.tabBookings).setOnClickListener(v -> showBookingPanel());
    }

    private void setupRoomTabs() {
        roomLayout.findViewById(R.id.tabGuests).setOnClickListener(v -> showGuestPanel());
        roomLayout.findViewById(R.id.tabRooms).setOnClickListener(v -> showRoomPanel());
        roomLayout.findViewById(R.id.tabBookings).setOnClickListener(v -> showBookingPanel());
    }

    private void setupBookingTabs() {
        bookingLayout.findViewById(R.id.tabGuests).setOnClickListener(v -> showGuestPanel());
        bookingLayout.findViewById(R.id.tabRooms).setOnClickListener(v -> showRoomPanel());
        bookingLayout.findViewById(R.id.tabBookings).setOnClickListener(v -> showBookingPanel());
    }

    private void setupSignOutButton(View layout) {
        Button signOutBtn = layout.findViewById(R.id.btnAdminSignOut);
        if (signOutBtn != null) {
            signOutBtn.setOnClickListener(v -> {
                SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
                prefs.edit().clear().apply();

                Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }

    private void showGuestPanel() {
        setContentView(guestLayout);
        initializeGuestViews();
        setupTabButtons();
        loadGuests();
    }

    private void initializeGuestViews() {
        inputGuestId = guestLayout.findViewById(R.id.inputGuestId);
        inputGuestName = guestLayout.findViewById(R.id.inputGuestName);
        inputGuestEmail = guestLayout.findViewById(R.id.inputGuestEmail);
        inputGuestPhone = guestLayout.findViewById(R.id.inputGuestPhone);
        guestListContainer = guestLayout.findViewById(R.id.guestListContainer);
        btnAddGuest = guestLayout.findViewById(R.id.btnAddGuest);

        btnAddGuest.setOnClickListener(v -> addGuest());
    }

    private void addGuest() {
        String name = inputGuestName.getText().toString().trim();
        String email = inputGuestEmail.getText().toString().trim();
        String phone = inputGuestPhone.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Guest guest = new Guest(name, email, phone);
        db.guestDao().insert(guest);

        clearGuestInputs();
        loadGuests();
        Toast.makeText(this, "Guest added!", Toast.LENGTH_SHORT).show();
    }

    private void loadGuests() {
        guestListContainer.removeAllViews();
        List<Guest> guests = db.guestDao().getAllGuests();

        for (Guest guest : guests) {
            View guestCard = LayoutInflater.from(this).inflate(R.layout.item_guest_card, guestListContainer, false);

            TextView tvGuestId = guestCard.findViewById(R.id.tvGuestId);
            TextView tvGuestName = guestCard.findViewById(R.id.tvGuestName);
            TextView tvGuestEmail = guestCard.findViewById(R.id.tvGuestEmail);
            TextView tvGuestPhone = guestCard.findViewById(R.id.tvGuestPhone);
            Button btnEdit = guestCard.findViewById(R.id.btnEditGuest);
            Button btnDelete = guestCard.findViewById(R.id.btnDeleteGuest);

            tvGuestId.setText("ID: G" + guest.getGuestId());
            tvGuestName.setText("Name: " + guest.getGuestName());
            tvGuestEmail.setText("Email: " + guest.getEmail());
            tvGuestPhone.setText("Phone: " + guest.getPhoneNumber());

            btnEdit.setOnClickListener(v -> editGuest(guest));
            btnDelete.setOnClickListener(v -> deleteGuest(guest));

            guestListContainer.addView(guestCard);
        }
    }

    private void editGuest(Guest guest) {
        inputGuestId.setText(String.valueOf(guest.getGuestId()));
        inputGuestName.setText(guest.getGuestName());
        inputGuestEmail.setText(guest.getEmail());
        inputGuestPhone.setText(guest.getPhoneNumber());

        btnAddGuest.setText("UPDATE GUEST");
        btnAddGuest.setOnClickListener(v -> {
            guest.setGuestName(inputGuestName.getText().toString().trim());
            guest.setEmail(inputGuestEmail.getText().toString().trim());
            guest.setPhoneNumber(inputGuestPhone.getText().toString().trim());

            db.guestDao().update(guest);
            clearGuestInputs();
            btnAddGuest.setText("ADD GUEST");
            btnAddGuest.setOnClickListener(view -> addGuest());
            loadGuests();
            Toast.makeText(this, "Guest updated!", Toast.LENGTH_SHORT).show();
        });
    }

    private void deleteGuest(Guest guest) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Guest")
                .setMessage("Delete " + guest.getGuestName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    db.guestDao().delete(guest);
                    loadGuests();
                    Toast.makeText(this, "Guest deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void clearGuestInputs() {
        inputGuestId.setText("");
        inputGuestName.setText("");
        inputGuestEmail.setText("");
        inputGuestPhone.setText("");
    }

    private void showRoomPanel() {
        setContentView(roomLayout);
        initializeRoomViews();
        setupTabButtons();
        loadRooms();
    }

    private void initializeRoomViews() {
        inputRoomId = roomLayout.findViewById(R.id.inputRoomId);
        inputRoomType = roomLayout.findViewById(R.id.inputRoomType);
        inputRoomPrice = roomLayout.findViewById(R.id.inputRoomPrice);
        inputRoomStatus = roomLayout.findViewById(R.id.inputRoomStatus);
        roomListContainer = roomLayout.findViewById(R.id.roomListContainer);
        bAddRoom = roomLayout.findViewById(R.id.b_AddRoom);

        bAddRoom.setOnClickListener(v -> addRoom());
    }

    private void addRoom() {
        String type = inputRoomType.getText().toString().trim();
        String priceStr = inputRoomPrice.getText().toString().trim();
        String status = inputRoomStatus.getText().toString().trim();

        if (type.isEmpty() || priceStr.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        Room room = new Room(type, price, status);
        db.roomDao().insert(room);

        clearRoomInputs();
        loadRooms();
        Toast.makeText(this, "Room added!", Toast.LENGTH_SHORT).show();
    }

    private void loadRooms() {
        roomListContainer.removeAllViews();
        List<Room> rooms = db.roomDao().getAllRooms();

        for (Room room : rooms) {
            View roomCard = LayoutInflater.from(this).inflate(R.layout.item_room_card, roomListContainer, false);

            TextView tvRoomId = roomCard.findViewById(R.id.tvRoomId);
            TextView tvRoomType = roomCard.findViewById(R.id.tvRoomType);
            TextView tvRoomPrice = roomCard.findViewById(R.id.tvRoomPrice);
            TextView tvRoomStatus = roomCard.findViewById(R.id.tvRoomStatus);
            Button btnUpdate = roomCard.findViewById(R.id.b_update);
            Button btnDelete = roomCard.findViewById(R.id.delete);

            tvRoomId.setText("ID: R" + room.getRoomId());
            tvRoomType.setText("Type: " + room.getRoomType());
            tvRoomPrice.setText("Price: ₱" + room.getPrice());
            tvRoomStatus.setText(room.getStatus());

            if (room.getStatus().equalsIgnoreCase("Available")) {
                tvRoomStatus.setBackgroundColor(Color.parseColor("#1B5E20"));
                tvRoomStatus.setTextColor(Color.parseColor("#4CAF50"));
            } else {
                tvRoomStatus.setBackgroundColor(Color.parseColor("#B71C1C"));
                tvRoomStatus.setTextColor(Color.parseColor("#FF5252"));
            }

            btnUpdate.setOnClickListener(v -> editRoom(room));
            btnDelete.setOnClickListener(v -> deleteRoom(room));

            roomListContainer.addView(roomCard);
        }
    }

    private void editRoom(Room room) {
        inputRoomId.setText(String.valueOf(room.getRoomId()));
        inputRoomType.setText(room.getRoomType());
        inputRoomPrice.setText(String.valueOf(room.getPrice()));
        inputRoomStatus.setText(room.getStatus());

        bAddRoom.setText("UPDATE ROOM");
        bAddRoom.setOnClickListener(v -> {
            room.setRoomType(inputRoomType.getText().toString().trim());
            room.setPrice(Double.parseDouble(inputRoomPrice.getText().toString().trim()));
            room.setStatus(inputRoomStatus.getText().toString().trim());

            db.roomDao().update(room);
            clearRoomInputs();
            bAddRoom.setText("ADD ROOM");
            bAddRoom.setOnClickListener(view -> addRoom());
            loadRooms();
            Toast.makeText(this, "Room updated!", Toast.LENGTH_SHORT).show();
        });
    }

    private void deleteRoom(Room room) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Room")
                .setMessage("Delete Room " + room.getRoomId() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    db.roomDao().delete(room);
                    loadRooms();
                    Toast.makeText(this, "Room deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void clearRoomInputs() {
        inputRoomId.setText("");
        inputRoomType.setText("");
        inputRoomPrice.setText("");
        inputRoomStatus.setText("");
    }

    private void showBookingPanel() {
        setContentView(bookingLayout);
        initializeBookingViews();
        setupTabButtons();
        loadBookings();
    }

    private void initializeBookingViews() {
        etUsernameInput = bookingLayout.findViewById(R.id.et_usernameInput);
        etInputBookingRoomId = bookingLayout.findViewById(R.id.et_inputBookingRoomId);
        etInputCheckIn = bookingLayout.findViewById(R.id.et_inputCheckIn);
        etInputCheckOut = bookingLayout.findViewById(R.id.et_inputCheckOut);
        etInputTotalPrice = bookingLayout.findViewById(R.id.et_inputTotalPrice);
        bookingListContainer = bookingLayout.findViewById(R.id.bookingListContainer);
        bAddBooking = bookingLayout.findViewById(R.id.b_AddBooking);

        bAddBooking.setOnClickListener(v -> addBooking());
    }

    private void addBooking() {
        String guestIdStr = etUsernameInput.getText().toString().trim();
        String roomIdStr = etInputBookingRoomId.getText().toString().trim();
        String checkIn = etInputCheckIn.getText().toString().trim();
        String checkOut = etInputCheckOut.getText().toString().trim();
        String priceStr = etInputTotalPrice.getText().toString().trim();

        if (guestIdStr.isEmpty() || roomIdStr.isEmpty() || checkIn.isEmpty() ||
                checkOut.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int guestId = Integer.parseInt(guestIdStr);
        int roomId = Integer.parseInt(roomIdStr);
        double price = Double.parseDouble(priceStr);

        Booking booking = new Booking(guestId, roomId, checkIn, checkOut, price);
        db.bookingDao().insert(booking);

        db.roomDao().updateRoomStatus(roomId, "Occupied");

        clearBookingInputs();
        loadBookings();
        Toast.makeText(this, "Booking added! Room marked as Occupied", Toast.LENGTH_SHORT).show();
    }

    private void loadBookings() {
        bookingListContainer.removeAllViews();
        List<Booking> bookings = db.bookingDao().getAllBookings();

        for (Booking booking : bookings) {
            View bookingCard = LayoutInflater.from(this).inflate(R.layout.item_booking_card, bookingListContainer, false);

            TextView tvBookingId = bookingCard.findViewById(R.id.tvBookingId);
            TextView tvGuest = bookingCard.findViewById(R.id.tv_guest);
            TextView tvRoom = bookingCard.findViewById(R.id.tv_room);
            TextView tvCheckIn = bookingCard.findViewById(R.id.tv_checkIn);
            TextView tvCheckOut = bookingCard.findViewById(R.id.tv_checkOut);
            TextView tvTotal = bookingCard.findViewById(R.id.tv_total);
            Button btnEdit = bookingCard.findViewById(R.id.b_edit);
            Button btnDelete = bookingCard.findViewById(R.id.b_delete);

            Guest guest = db.guestDao().getGuestById(booking.getGuestId());
            Room room = db.roomDao().getRoomById(booking.getRoomId());

            String guestName = (guest != null) ? guest.getGuestName() : "Unknown";
            String roomType = (room != null) ? room.getRoomType() : "Unknown";

            tvBookingId.setText("Booking #" + booking.getBookingId());
            tvGuest.setText("Guest: " + guestName);
            tvRoom.setText("Room: " + roomType);
            tvCheckIn.setText("Check-in: " + booking.getCheckInDate());
            tvCheckOut.setText("Check-out: " + booking.getCheckOutDate());
            tvTotal.setText("Total: ₱" + booking.getTotalPrice());

            btnEdit.setOnClickListener(v -> editBooking(booking));
            btnDelete.setOnClickListener(v -> deleteBooking(booking));

            bookingListContainer.addView(bookingCard);
        }
    }

    private void editBooking(Booking booking) {
        etUsernameInput.setText(String.valueOf(booking.getGuestId()));
        etInputBookingRoomId.setText(String.valueOf(booking.getRoomId()));
        etInputCheckIn.setText(booking.getCheckInDate());
        etInputCheckOut.setText(booking.getCheckOutDate());
        etInputTotalPrice.setText(String.valueOf(booking.getTotalPrice()));

        bAddBooking.setText("UPDATE BOOKING");
        bAddBooking.setOnClickListener(v -> {
            booking.setGuestId(Integer.parseInt(etUsernameInput.getText().toString().trim()));
            booking.setRoomId(Integer.parseInt(etInputBookingRoomId.getText().toString().trim()));
            booking.setCheckInDate(etInputCheckIn.getText().toString().trim());
            booking.setCheckOutDate(etInputCheckOut.getText().toString().trim());
            booking.setTotalPrice(Double.parseDouble(etInputTotalPrice.getText().toString().trim()));

            db.bookingDao().update(booking);
            clearBookingInputs();
            bAddBooking.setText("ADD BOOKING");
            bAddBooking.setOnClickListener(view -> addBooking());
            loadBookings();
            Toast.makeText(this, "Booking updated!", Toast.LENGTH_SHORT).show();
        });
    }

    private void deleteBooking(Booking booking) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Booking")
                .setMessage("Delete this booking? Room will be marked as Available.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    db.roomDao().updateRoomStatus(booking.getRoomId(), "Available");
                    db.bookingDao().delete(booking);
                    loadBookings();
                    Toast.makeText(this, "Booking deleted, Room is now Available", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void clearBookingInputs() {
        etUsernameInput.setText("");
        etInputBookingRoomId.setText("");
        etInputCheckIn.setText("");
        etInputCheckOut.setText("");
        etInputTotalPrice.setText("");
    }
}