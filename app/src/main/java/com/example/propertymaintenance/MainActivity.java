package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.goToHousingInfo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.goToHousingInfo) {
            Intent infoIntent = new Intent(this, HousingInfo.class);
            startActivity(infoIntent);
        }
    }
}
