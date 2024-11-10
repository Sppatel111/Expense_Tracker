package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnManageTransactions, btnViewReports, btnExit;
    ImageButton btnUserDetails, btnPasswordManager, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
        btnManageTransactions = findViewById(R.id.btnManageTransactions);
        btnViewReports = findViewById(R.id.btnViewReports);
        btnExit = findViewById(R.id.btnExit);

        btnUserDetails = findViewById(R.id.btnUserDetails);
        btnPasswordManager = findViewById(R.id.btnPasswordManager);
        btnSettings = findViewById(R.id.btnSettings);

        // Set click listeners

        // Manage Transactions button
        btnManageTransactions.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ManageTransactionsActivity.class);
            startActivity(intent);
        });

        // View Reports button
        btnViewReports.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ViewReportsActivity.class);
            startActivity(intent);
        });

        // User Details button
        btnUserDetails.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserDetailsActivity.class);
            startActivity(intent);
        });

        // Password Manager button
        btnPasswordManager.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PasswordManagerActivity.class);
            startActivity(intent);
        });

        // Settings button
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Exit button
        btnExit.setOnClickListener(v -> showExitConfirmationDialog());
    }

    // Method to show exit confirmation dialog
    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(HomeActivity.this, "Exiting app", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                })
                .setNegativeButton("No", null)
                .show();
    }
}
