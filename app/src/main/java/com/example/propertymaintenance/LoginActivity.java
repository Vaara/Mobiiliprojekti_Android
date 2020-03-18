package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static android.preference.PreferenceManager.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences appPreferences;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.buttonLogin).setOnClickListener(this);

        appPreferences = getDefaultSharedPreferences(this);
        preferenceEditor = appPreferences.edit();

        preferenceEditor.putString("userId", "1234");
        preferenceEditor.putString("userLevel", "0");
        preferenceEditor.putString("userFullName", "Kimmo Juusola");
        preferenceEditor.commit();
        preferenceEditor.remove("userFullName");
        preferenceEditor.commit();

        Log.d("Name", appPreferences.getString("userFullName", "Not found"));
    }

    @Override
    public void onClick(View view) {

    }
}
