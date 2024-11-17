package com.example.test;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {

    EditText etDate, etCategory, etAmount, etDescription;
    Button btnSave;
    DatabaseHelper db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        etDate = findViewById(R.id.etDate);
        etCategory = findViewById(R.id.etCategory);
        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);
        db = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {
            String date = etDate.getText().toString();
            String category = etCategory.getText().toString();
            double amount = Double.parseDouble(etAmount.getText().toString());
            String description = etDescription.getText().toString();

            sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String username = sharedPreferences.getString("loggedInUser", "Guest");

            boolean inserted = db.insertTransaction(username, date, category, amount, description);
            if (inserted) {
                Toast.makeText(this, "Transaction added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add transaction", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
