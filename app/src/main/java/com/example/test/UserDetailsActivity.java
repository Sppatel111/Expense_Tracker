package com.example.test;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    TextView tvUserName, tvUserEmail, tvUserPhone;
    DatabaseHelper dbHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserPhone = findViewById(R.id.tvUserPhone);

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("loggedInUser", "Guest");

        // Fetch user details from the database
        Cursor cursor = dbHelper.getUserDetails(username);

        if (cursor != null && cursor.moveToFirst()) {
            // Get user details from the cursor
            String userName = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String userPhone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));

            // Set user details to TextViews
            tvUserName.setText("User Name: " + userName);
            tvUserEmail.setText("Email: " + userEmail);
            tvUserPhone.setText("Phone: " + userPhone);

            // Close the cursor
            cursor.close();
        } else {
            // Display default values if no data is found
            tvUserName.setText("User Name: Not Found");
            tvUserEmail.setText("Email: Not Found");
            tvUserPhone.setText("Phone: Not Found");
        }
    }
}
