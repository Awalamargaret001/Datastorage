package com.example.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id"; // Primary key, convention for Android
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    // SQL statement to create the users table
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT NOT NULL," +
                    COLUMN_EMAIL + " TEXT NOT NULL UNIQUE" + // Email should be unique
                    ");";

    /**
     * Constructor for DBHelper.
     * @param context The context of the application.
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This method is called when the database is created for the first time.
        // Execute the SQL statement to create the users table.
        db.execSQL(CREATE_TABLE_USERS);
        Log.d("DBHelper", "Database table 'users' created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called when the database needs to be upgraded.
        // For simplicity, we drop the existing table and recreate it.
        // In a real application, you would handle migrations more carefully
        // to preserve user data.
        Log.w("DBHelper", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
