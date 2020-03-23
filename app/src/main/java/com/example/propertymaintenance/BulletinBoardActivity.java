package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class BulletinBoardActivity extends AppCompatActivity {
    ListView listViewBulletinBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        listViewBulletinBoard = findViewById(R.id.listViewBulletinBoard);

        listViewBulletinBoard.setAdapter(new BulletinBoardAdapter(this));

    }
}
