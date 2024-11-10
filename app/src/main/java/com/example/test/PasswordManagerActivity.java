package com.example.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordManagerActivity extends AppCompatActivity {

    EditText etOldPassword, etNewPassword, etConfirmPassword;
    Button btnChangePassword;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);

        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        db = new DatabaseHelper(this);

        // Change password logic
        btnChangePassword.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (newPassword.equals(confirmPassword)) {
            boolean updated = db.updatePassword(oldPassword, newPassword);
            if (updated) {
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Incorrect old password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }
}
