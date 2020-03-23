package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonActivity).setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
    launchActivity();
    }
    private void launchActivity() {

        Intent intent = new Intent(this, ServiceAdvices.class);
        startActivity(intent);
    }
}
