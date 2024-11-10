package com.example.test;
import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ExpenseTracker.db";
    private static final String TABLE_USERS = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_PASSWORD = "password";

    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_CATEGORY = "category";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USERNAME + " TEXT PRIMARY KEY, " +
                COL_EMAIL + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createTable);

        String createTransactionsTable = "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_AMOUNT + " REAL, " +
                COL_DESCRIPTION + " TEXT)";
        db.execSQL(createTransactionsTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    // for login signup
    // Method to insert a new user
    public boolean insertUser(String username, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Method to check if a user exists
    public boolean checkUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + " = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Method to check user credentials for login
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }



    // for transaction
    // Insert a new transaction
    public boolean insertTransaction(String date, String category, double amount, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_CATEGORY, category);
        values.put(COL_AMOUNT, amount);
        values.put(COL_DESCRIPTION, description);
        long result = db.insert(TABLE_TRANSACTIONS, null, values);
        return result != -1;
        }

    // Get all transactions
    public Cursor getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS, null);
    }

    // Update a transaction
    public boolean updateTransaction(int id, String date, String category, double amount, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_CATEGORY, category);
        values.put(COL_AMOUNT, amount);
        values.put(COL_DESCRIPTION, description);
        int result = db.update(TABLE_TRANSACTIONS, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Delete a transaction
    public boolean deleteTransaction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TRANSACTIONS, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Method to get all reports for ViewReportsActivity
    public Cursor getAllReports() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM reports"; // Replace 'reports' with your actual table name
        return db.rawQuery(query, null);
    }

    // Method to verify the current password for PasswordManagerActivity
    public boolean verifyPassword(String currentPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{currentPassword});

        boolean isVerified = cursor.getCount() > 0;
        cursor.close();
        return isVerified;
    }

    // Update password method
    public boolean updatePassword(String oldPassword, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE password = ?", new String[]{oldPassword});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("password", newPassword);
            int result = db.update("users", values, "password = ?", new String[]{oldPassword});
            cursor.close();
            return result > 0;
        } else {
            cursor.close();
            return false;
        }
    }

    public Cursor getTransactionById(int transactionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                "transactions",       // Table name
                null,                 // Select all columns
                "id = ?",             // WHERE condition
                new String[]{String.valueOf(transactionId)},  // WHERE arguments
                null,                 // Group By
                null,                 // Having
                null                  // Order By
        );
    }




}


