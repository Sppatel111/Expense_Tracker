package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    ImageView btnManageTransactions, btnViewReports;
    Button btnExit;
    TextView greetingText;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Initialize UI elements
        btnManageTransactions = findViewById(R.id.expenseAddIcon);
        btnViewReports = findViewById(R.id.reportAddIcon);
        btnExit = findViewById(R.id.btnExit);
        greetingText = findViewById(R.id.greetingText);

        // Fetch username from SharedPreferences or set a default
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("loggedInUser", "Guest");
        greetingText.setText("Hey, " + username + "!");

        // Set click listeners
        btnManageTransactions.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ManageTransactionsActivity.class);
            startActivity(intent);
        });

        btnViewReports.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ViewReportsActivity.class);
            startActivity(intent);
        });

        btnExit.setOnClickListener(v -> showExitConfirmationDialog());
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(HomeActivity.this, "Exiting app", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.action_logout:
                showLogoutConfirmationDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(HomeActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
                    // Clear user data and redirect to login screen
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("loggedInUser"); // Optional: remove the username
                    editor.apply();

                    Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(loginIntent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
