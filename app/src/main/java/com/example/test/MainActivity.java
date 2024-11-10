package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnManageTransactions, btnViewReports, btnExit;
    ImageView imgSettings, imgPasswordManager, imgUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btnManageTransactions = findViewById(R.id.btnManageTransactions);
        btnViewReports = findViewById(R.id.btnViewReports);
        btnExit = findViewById(R.id.btnExit);

        // Initialize icons
        imgSettings = findViewById(R.id.imgSettings);
        imgPasswordManager = findViewById(R.id.imgPasswordManager);
        imgUserDetails = findViewById(R.id.imgUserDetails);

        // Button click listeners
        btnManageTransactions.setOnClickListener(v -> openManageTransactions());
        btnViewReports.setOnClickListener(v -> openViewReports());
        btnExit.setOnClickListener(v -> exitApp());

        // Icon click listeners
        imgSettings.setOnClickListener(v -> openSettings());
        imgPasswordManager.setOnClickListener(v -> openPasswordManager());
        imgUserDetails.setOnClickListener(v -> openUserDetails());
    }

    private void openManageTransactions() {
        Intent intent = new Intent(MainActivity.this, ManageTransactionsActivity.class);
        startActivity(intent);
    }

    private void openViewReports() {
        Intent intent = new Intent(MainActivity.this, ViewReportsActivity.class);
        startActivity(intent);
    }

    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openPasswordManager() {
        Intent intent = new Intent(MainActivity.this, PasswordManagerActivity.class);
        startActivity(intent);
    }

    private void openUserDetails() {
        Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
        startActivity(intent);
    }

    private void exitApp() {
        Toast.makeText(this, "Exiting the app", Toast.LENGTH_SHORT).show();
        finish();
    }
}
