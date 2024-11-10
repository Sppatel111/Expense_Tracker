package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    ImageView imgSettingsIcon, imgUserIcon, imgPasswordIcon;
    TextView tvUserDetails, tvPasswordManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        imgSettingsIcon = findViewById(R.id.imgSettingsIcon);
        imgUserIcon = findViewById(R.id.imgUserIcon);
        imgPasswordIcon = findViewById(R.id.imgPasswordIcon);
        tvUserDetails = findViewById(R.id.tvUserDetails);
        tvPasswordManager = findViewById(R.id.tvPasswordManager);

        // Set click listeners
        tvUserDetails.setOnClickListener(v -> showUserDetails());
        tvPasswordManager.setOnClickListener(v -> openPasswordManager());

        imgUserIcon.setOnClickListener(v -> showUserDetails());
        imgPasswordIcon.setOnClickListener(v -> openPasswordManager());
    }

    // Display user details (this can be expanded based on your requirements)
    private void showUserDetails() {
        Toast.makeText(this, "User Details: Sneha Patel", Toast.LENGTH_SHORT).show();
        // You can navigate to a UserDetailsActivity if needed
    }

    // Open the PasswordManagerActivity
    private void openPasswordManager() {
        Intent intent = new Intent(SettingsActivity.this, PasswordManagerActivity.class);
        startActivity(intent);
    }
}
