package com.example.test;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    TextView tvUserName, tvUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);

        // Retrieve user details (for demo purposes, using static values)
        String userName = "John Doe";
        String userEmail = "john.doe@example.com";

        // Set user details to TextViews
        tvUserName.setText("User Name: " + userName);
        tvUserEmail.setText("Email: " + userEmail);
    }
}

