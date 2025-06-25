package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main.xml layout
        setContentView(R.layout.activity_main);

        // Find the "Open Settings" button by its ID
        Button btnOpenSettings = findViewById(R.id.btnOpenSettings);
        // Set an OnClickListener to handle button clicks
        btnOpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the SettingsActivity
                Intent intent = new Intent(MainActivity.this, com.example.datastorage.SettingsActivity.class);
                // Start the SettingsActivity
                startActivity(intent);
            }
        });

        // Find the "Open User Registration" button by its ID
        Button btnOpenRegistration = findViewById(R.id.btnOpenRegistration);
        // Set an OnClickListener to handle button clicks
        btnOpenRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the UserRegistrationActivity
                Intent intent = new Intent(MainActivity.this, UserRegistrationActivity.class);
                // Start the UserRegistrationActivity
                startActivity(intent);
            }
        });
    }
}
