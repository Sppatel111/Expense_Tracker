package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ExpenseTracker.db";
    private static final int DATABASE_VERSION = 1;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_PASSWORD = "password";

    // Transaction Columns
    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_CATEGORY = "category";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USERNAME + " TEXT PRIMARY KEY, " +
                COL_EMAIL + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to insert a new user and create a transaction table for the user
    public boolean insertUser(String username, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_PASSWORD, password);

        // Insert user data into the users table
        long result = db.insert(TABLE_USERS, null, values);

        // Create a unique transactions table for this user
        if (result != -1) {
            String createUserTransactionsTable = "CREATE TABLE transactions_" + username + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_DATE + " TEXT, " +
                    COL_CATEGORY + " TEXT, " +
                    COL_AMOUNT + " REAL, " +
                    COL_DESCRIPTION + " TEXT)";
            db.execSQL(createUserTransactionsTable);
            return true;
        }
        return false;
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

    // Insert a new transaction for the specified user
    public boolean insertTransaction(String username, String date, String category, double amount, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_CATEGORY, category);
        values.put(COL_AMOUNT, amount);
        values.put(COL_DESCRIPTION, description);

        String tableName = "transactions_" + username;
        long result = db.insert(tableName, null, values);
        return result != -1;
    }

    // Update a transaction for the specified user
    public boolean updateTransaction(String username, int id, String date, String category, double amount, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_CATEGORY, category);
        values.put(COL_AMOUNT, amount);
        values.put(COL_DESCRIPTION, description);

        String tableName = "transactions_" + username;
        int result = db.update(tableName, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Delete a transaction for the specified user
    public boolean deleteTransaction(String username, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = "transactions_" + username;
        int result = db.delete(tableName, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Get all transactions for the specified user
    public Cursor getAllTransactions(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = "transactions_" + username;
        return db.rawQuery("SELECT * FROM " + tableName, null);
    }

    // Get a transaction by ID for the specified user
    public Cursor getTransactionById(String username, int transactionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = "transactions_" + username;
        return db.query(
                tableName,       // User-specific table name
                null,            // Select all columns
                "id = ?",        // WHERE condition
                new String[]{String.valueOf(transactionId)},  // WHERE arguments
                null,            // Group By
                null,            // Having
                null             // Order By
        );
    }

    // Method to verify the current password for PasswordManagerActivity
    public boolean verifyPassword(String currentPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE password = ?", new String[]{currentPassword});
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
            values.put(COL_PASSWORD, newPassword);
            int result = db.update(TABLE_USERS, values, "password = ?", new String[]{oldPassword});
            cursor.close();
            return result > 0;
        } else {
            cursor.close();
            return false;
        }
    }

    // Method to get user details
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT username, email, phone FROM " + TABLE_USERS + " WHERE username = ? LIMIT 1";
        return db.rawQuery(query, new String[]{username});
    }

}
