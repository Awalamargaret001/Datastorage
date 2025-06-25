package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datastorage.DBHelper;
import com.example.datastorage.R;

public class UserRegistrationActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etRegistrationEmail; // Renamed to avoid conflict with SettingsActivity's email EditText
    private Button btnRegisterUser;
    private Button btnViewUsers;
    private TextView tvUserList; // TextView to display the list of users

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Initialize UI components
        etName = findViewById(R.id.etName);
        etRegistrationEmail = findViewById(R.id.etRegistrationEmail);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnViewUsers = findViewById(R.id.btnViewUsers);
        tvUserList = findViewById(R.id.tvUserList);

        // Initialize the DBHelper
        dbHelper = new DBHelper(this);

        // Set OnClickListener for Register User button
        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Set OnClickListener for View Users button
        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUsers();
            }
        });
    }

    /**
     * Handles the logic for registering a new user into the SQLite database.
     */
    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etRegistrationEmail.getText().toString().trim();

        // Basic input validation
        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter both name and email.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get a writable instance of the database
        SQLiteDatabase db;
        db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_EMAIL, email);

        // Insert the new user into the 'users' table
        // The insert method returns the row ID of the newly inserted row, or -1 if an error occurred
        long newRowId = db.insert(DBHelper.TABLE_USERS, null, values);

        // Close the database connection
        db.close();

        // Provide feedback to the user
        if (newRowId != -1) {
            Toast.makeText(this, "User registered successfully! ID: " + newRowId, Toast.LENGTH_SHORT).show();
            // Clear the input fields after successful registration
            etName.setText("");
            etRegistrationEmail.setText("");
            // Refresh the user list
            viewUsers();
        } else {
            Toast.makeText(this, "Error registering user. Email might already exist.", Toast.LENGTH_LONG).show();
            Log.e("UserRegistration", "Error inserting user: " + name + ", " + email);
        }
    }

    /**
     * Handles the logic for viewing all registered users from the SQLite database.
     */
    private void viewUsers() {
        // Get a readable instance of the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                DBHelper.COLUMN_ID,
                DBHelper.COLUMN_NAME,
                DBHelper.COLUMN_EMAIL
        };

        // Perform a query to get all users from the 'users' table
        Cursor cursor = db.query(
                DBHelper.TABLE_USERS,   // The table to query
                projection,             // The columns to return
                null,                   // The columns for the WHERE clause
                null,                   // The values for the WHERE clause
                null,                   // Don't group the rows
                null,                   // Don't filter by row groups
                null                    // The sort order
        );

        StringBuilder userListBuilder = new StringBuilder();
        userListBuilder.append("Registered Users:\n\n");

        // Loop through the cursor to read each user's data
        if (cursor.moveToFirst()) {
            do {
                // Get column indices
                int nameColumnIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                int emailColumnIndex = cursor.getColumnIndex(DBHelper.COLUMN_EMAIL);

                // Check if the column exists before retrieving its value
                String name = (nameColumnIndex != -1) ? cursor.getString(nameColumnIndex) : "N/A";
                String email = (emailColumnIndex != -1) ? cursor.getString(emailColumnIndex) : "N/A";

                // Log the user data
                Log.d("User Data", "Name: " + name + ", Email: " + email);
                userListBuilder.append("Name: ").append(name).append(", Email: ").append(email).append("\n");

            } while (cursor.moveToNext());
        } else {
            Log.d("User Data", "No users found.");
            userListBuilder.append("No users registered yet.");
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        // Display the user list in the TextView
        tvUserList.setText(userListBuilder.toString());
        Toast.makeText(this, "Users loaded.", Toast.LENGTH_SHORT).show();
    }
}
