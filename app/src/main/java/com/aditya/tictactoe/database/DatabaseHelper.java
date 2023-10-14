package com.aditya.tictactoe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // The FeedEntry class defines schema of your table
    public static class FeedEntry {
        //Database name
        private static final String DATABASE_NAME = "Users.db";
        //Database version
        private static final int DATABASE_VERSION = 1;
        //Database Table Name
        private static final String TABLE_USERS = "users";

        //Table - Column ID
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_USERNAME = "username";
        private static final String COLUMN_EMAIL = "email";
        private static final String COLUMN_PASSWORD = "password";

    }

    public DatabaseHelper(Context context) {
        super(context,FeedEntry.DATABASE_NAME, null , FeedEntry.DATABASE_VERSION);
    }


    // Define the SQL statement for deleting the table
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_USERS;


    // Define the SQL statement for creating the table
    private static final  String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_USERS + " (" +
                    FeedEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_USERNAME + " TEXT UNIQUE," +
                    FeedEntry.COLUMN_EMAIL + " TEXT UNIQUE ," +
                    FeedEntry.COLUMN_PASSWORD + " TEXT)";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Execute the SQL statement to create the table
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }
    public void insertData(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_USERNAME, username);
        values.put(FeedEntry.COLUMN_EMAIL, email);
        values.put(FeedEntry.COLUMN_PASSWORD, password);

        // Insert the new row
        db.insert(FeedEntry.TABLE_USERS, null, values);
        db.close();
    }

    public String getData() {
            SQLiteDatabase db = this.getReadableDatabase();
            // Perform query operation here
            db.close();
            return null;
        }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Method to read data from the database and cross-check username and password
    public boolean checkCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {FeedEntry.COLUMN_USERNAME, FeedEntry.COLUMN_PASSWORD};
        String selection = FeedEntry.COLUMN_USERNAME + " = ? AND " + FeedEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(
                FeedEntry.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isMatch = cursor.getCount() > 0;

        cursor.close();
        db.close();
        return isMatch;
    }
}
