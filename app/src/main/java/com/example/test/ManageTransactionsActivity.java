package com.example.test;


import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ManageTransactionsActivity extends AppCompatActivity {

    ListView lvTransactions;
    Button btnAddTransaction;
    DatabaseHelper db;
    ArrayList<String> transactionList;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_transactions);

        lvTransactions = findViewById(R.id.lvTransactions);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);
        db = new DatabaseHelper(this);
        transactionList = new ArrayList<>();

        // Load transactions into the list view
        loadTransactions();

        // Open AddTransactionActivity to add a new transaction
        btnAddTransaction.setOnClickListener(v -> {
            startActivity(new Intent(ManageTransactionsActivity.this, AddTransactionActivity.class));
        });

        // Handle item click for edit or delete options
        lvTransactions.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTransaction = transactionList.get(position);

            // Extract the transaction ID (assuming the format "ID: Date - Category - Amount - Description")
            int transactionId = Integer.parseInt(selectedTransaction.split(":")[0]);

            // Show options to edit or delete the transaction
            new AlertDialog.Builder(this)
                    .setTitle("Manage Transaction")
                    .setMessage("Choose an action")
                    .setPositiveButton("Edit", (dialog, which) -> {
                        Intent intent = new Intent(ManageTransactionsActivity.this, EditTransactionActivity.class);
                        intent.putExtra("TRANSACTION_ID", transactionId);
                        startActivity(intent);
                    })
                    .setNegativeButton("Delete", (dialog, which) -> {
                        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        String username = sharedPreferences.getString("loggedInUser", "Guest");

                        boolean deleted = db.deleteTransaction(username, transactionId);
                        if (deleted) {
                            Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show();
                            loadTransactions(); // Refresh the list after deletion
                        } else {
                            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNeutralButton("Cancel", null)
                    .show();
        });
    }
    private void loadTransactions() {
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("loggedInUser", "Guest");

        Cursor cursor = db.getAllTransactions(username);
        transactionList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            // Get column indices
            int idIndex = cursor.getColumnIndex("id");
            int dateIndex = cursor.getColumnIndex("date");
            int categoryIndex = cursor.getColumnIndex("category");
            int amountIndex = cursor.getColumnIndex("amount");
            int descriptionIndex = cursor.getColumnIndex("description");

            if (idIndex == -1 || dateIndex == -1 || categoryIndex == -1 || amountIndex == -1 || descriptionIndex == -1) {
                Toast.makeText(this, "Error: Missing columns in database", Toast.LENGTH_SHORT).show();
                return;
            }

            // Iterate through the cursor and populate the list
            do {
                int id = cursor.getInt(idIndex);
                String date = cursor.getString(dateIndex);
                String category = cursor.getString(categoryIndex);
                double amount = cursor.getDouble(amountIndex);
                String description = cursor.getString(descriptionIndex);

                // Add formatted transaction details to the list
                transactionList.add(id + ": " + date + " - " + category + " - $" + amount + " - " + description);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // Update the adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactionList);
        lvTransactions.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Reload transactions when returning to this activity
        loadTransactions();
    }
}
