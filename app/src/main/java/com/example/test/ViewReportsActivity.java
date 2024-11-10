package com.example.test;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewReportsActivity extends AppCompatActivity {

    ListView lvReports;
    DatabaseHelper db;
    ArrayList<String> reportList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        lvReports = findViewById(R.id.lvReports);
        db = new DatabaseHelper(this);
        reportList = new ArrayList<>();

        // Load reports or transaction data
        loadReports();

        // Handle item click for viewing specific report details
        lvReports.setOnItemClickListener((parent, view, position, id) -> {
            String selectedReport = reportList.get(position);
            // You can show more details or navigate to a different screen
            Toast.makeText(this, "Clicked on: " + selectedReport, Toast.LENGTH_SHORT).show();
        });
    }

    private void loadReports() {
        Cursor cursor = db.getAllReports(); // Assuming you have a method for reports in DatabaseHelper
        reportList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            int reportIndex = cursor.getColumnIndex("report");
            // Iterate through the cursor and add reports to the list
            do {
                String report = cursor.getString(reportIndex);
                reportList.add(report);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // Update the adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reportList);
        lvReports.setAdapter(adapter);
    }
}
