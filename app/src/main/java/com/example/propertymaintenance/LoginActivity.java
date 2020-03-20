package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.preference.PreferenceManager.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USER_ID = "userId";
    public static final String USER_LEVEL = "userLevel";
    public static final String USER_NAME = "userName";

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View view) {
        checkLoginCredentials();
    }

    public void checkLoginCredentials() {

        // If loginOk == true
        if (true) {
            storeUserToSharedPrefs();
        }
        // If response is "Missing username or password"
            // See above
        // Else
            // DB query 1&2
            // If response is 404, user not found
            // Else response is res.json({ userId, userLevel, userFullName });

            //catch query 1&2
                // response is res.sendStatus(500);


        else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
        }
    }

    public void storeUserToSharedPrefs() {
        editor.putInt(USER_ID, 1234);
        editor.putInt(USER_LEVEL, 0);
        editor.putString(USER_NAME, "Test User");

        // If successfully stores data to SharesPreferences
        if (editor.commit()) {
            login();
        }

        else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            Log.d("Login", "Unable to store userdata to SharedPreferences");
        }
    }

    public void removeUserFromSharedPrefs() {
        editor.remove(USER_ID);
        editor.remove(USER_LEVEL);
        editor.remove(USER_NAME);

        if (!editor.commit()) {
            Log.d("Login", "Unable to remove userdata from SharedPreferences");
        }
    }

    public void login() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();  // Prevents users from coming back to this activity with backButton
    }

    public void logout() {
        removeUserFromSharedPrefs();
        // Show LoginScreen ?
    }
}
