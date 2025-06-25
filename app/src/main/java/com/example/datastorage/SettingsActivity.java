package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private Button btnSaveSettings;

    // Define keys for SharedPreferences
    private static final String PREFS_NAME = "UserSettings";
    private static final String KEY_USERNAME = "username_key";
    private static final String KEY_EMAIL = "email_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize EditText fields and Button
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);

        // Load saved settings when the activity is created
        loadUserSettings();

        // Set OnClickListener for the Save Settings button
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserSettings();
            }
        });
    }

    /**
     * Loads the user settings (username and email) from SharedPreferences
     * and displays them in the EditText fields.
     */
    private void loadUserSettings() {
        // Get a SharedPreferences instance with the specified name
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Retrieve the saved username and email, providing a default empty string if not found
        String username = prefs.getString(KEY_USERNAME, "");
        String email = prefs.getString(KEY_EMAIL, "");

        // Set the retrieved text to the EditText fields
        etUsername.setText(username);
        etEmail.setText(email);

        // Show a toast message if settings were loaded (optional, for demonstration)
        if (!username.isEmpty() || !email.isEmpty()) {
            Toast.makeText(this, "Previous settings loaded!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Saves the user settings (username and email) from the EditText fields
     * into SharedPreferences.
     */
    private void saveUserSettings() {
        // Get the current text from the EditText fields
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();

        // Get a SharedPreferences instance for editing
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Put the new username and email into the editor with their respective keys
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);

        // Apply the changes asynchronously to save them
        editor.apply();

        // Show a Toast message to confirm that settings have been saved
        Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
    }
}
