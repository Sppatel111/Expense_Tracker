package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class EditTransactionActivity extends AppCompatActivity {

    EditText etDate, etCategory, etAmount, etDescription;
    Button btnUpdate, btnDelete;
    DatabaseHelper db;
    int transactionId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        etDate = findViewById(R.id.etDate);
        etCategory = findViewById(R.id.etCategory);
        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);


        db = new DatabaseHelper(this);

        // Get the transaction ID from the Intent
        transactionId = getIntent().getIntExtra("TRANSACTION_ID", -1);

        // Load the transaction details
        loadTransactionDetails(transactionId);

        // Update button click listener
        btnUpdate.setOnClickListener(v -> updateTransaction());

        // Delete button click listener
        btnDelete.setOnClickListener(v -> deleteTransaction());
    }

    private void loadTransactionDetails(int transactionId) {

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("loggedInUser", "Guest");

        Cursor cursor = db.getTransactionById(username, transactionId);

        if (cursor != null && cursor.moveToFirst()) {
            int dateIndex = cursor.getColumnIndex("date");
            int categoryIndex = cursor.getColumnIndex("category");
            int amountIndex = cursor.getColumnIndex("amount");
            int descriptionIndex = cursor.getColumnIndex("description");

            if (dateIndex == -1 || categoryIndex == -1 || amountIndex == -1 || descriptionIndex == -1) {
                Toast.makeText(this, "Error: Missing columns in database", Toast.LENGTH_SHORT).show();
                return;
            }

            String date = cursor.getString(dateIndex);
            String category = cursor.getString(categoryIndex);
            double amount = cursor.getDouble(amountIndex);
            String description = cursor.getString(descriptionIndex);

            etDate.setText(date);
            etCategory.setText(category);
            etAmount.setText(String.valueOf(amount));
            etDescription.setText(description);

            cursor.close();
        } else {
            Toast.makeText(this, "Transaction not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTransaction() {
        String date = etDate.getText().toString();
        String category = etCategory.getText().toString();
        String amountString = etAmount.getText().toString();
        String description = etDescription.getText().toString();

        if (date.isEmpty() || category.isEmpty() || amountString.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountString);
            sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String username = sharedPreferences.getString("loggedInUser", "Guest");

            if (db.updateTransaction(username, transactionId, date, category, amount, description)) {
                Toast.makeText(this, "Transaction updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update transaction", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTransaction() {
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("loggedInUser", "Guest");

        if (db.deleteTransaction(username, transactionId)) {
            Toast.makeText(this, "Transaction deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to delete transaction", Toast.LENGTH_SHORT).show();
        }
    }
}
