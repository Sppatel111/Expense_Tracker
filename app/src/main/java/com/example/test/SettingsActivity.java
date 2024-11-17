package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout userDetailsLayout, changePasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize layouts for user details and change password
        userDetailsLayout = findViewById(R.id.layoutUserDetails);
        changePasswordLayout = findViewById(R.id.layoutChangePassword);

        // Click listener for User Details
        userDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open User Details Activity
                Intent intent = new Intent(SettingsActivity.this, UserDetailsActivity.class);
                startActivity(intent);
            }
        });

        // Click listener for Change Password
        changePasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Password Manager Activity
                Intent intent = new Intent(SettingsActivity.this, PasswordManagerActivity.class);
                startActivity(intent);
            }
        });
    }
}
