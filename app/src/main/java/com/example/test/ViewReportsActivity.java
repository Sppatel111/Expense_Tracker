package com.example.test;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.SharedPreferences;

public class ViewReportsActivity extends AppCompatActivity {

    ListView lvReports;
    DatabaseHelper db;
    ArrayList<String> transactionList;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        lvReports = findViewById(R.id.lvReports);
        db = new DatabaseHelper(this);
        transactionList = new ArrayList<>();

        // Load all transactions
        loadTransactions();

        // Handle item click for viewing specific transaction details
        lvReports.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTransaction = transactionList.get(position);
            Toast.makeText(this, "Clicked on: " + selectedTransaction, Toast.LENGTH_SHORT).show();
        });
    }

    private void loadTransactions() {
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("loggedInUser", "Guest");
        Cursor cursor = db.getAllTransactions(username);
        transactionList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            // Get column indexes
            int idIndex = cursor.getColumnIndex("id");
            int dateIndex = cursor.getColumnIndex("date");
            int categoryIndex = cursor.getColumnIndex("category");
            int amountIndex = cursor.getColumnIndex("amount");
            int descriptionIndex = cursor.getColumnIndex("description");

            // Iterate through the cursor and add transactions to the list
            do {
                int id = cursor.getInt(idIndex);
                String date = cursor.getString(dateIndex);
                String category = cursor.getString(categoryIndex);
                double amount = cursor.getDouble(amountIndex);
                String description = cursor.getString(descriptionIndex);

                // Format the transaction details
                String transaction = "ID: " + id + "\nDate: " + date + "\nCategory: " + category +
                        "\nAmount: " + amount + "\nDescription: " + description;
                transactionList.add(transaction);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // Update the adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactionList);
        lvReports.setAdapter(adapter);
    }
}
