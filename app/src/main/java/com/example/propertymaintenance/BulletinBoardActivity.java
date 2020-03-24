package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class BulletinBoardActivity extends AppCompatActivity {
    ListView listViewBulletinBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        listViewBulletinBoard = findViewById(R.id.listViewBulletinBoard);

        listViewBulletinBoard.setAdapter(new BulletinBoardAdapter(this));
        
        setupToolbar();
    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.include3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView subtitle = (TextView) toolbar.findViewById(R.id.toolbar_subtitle);
        subtitle.setText(R.string.app_subtitle_bulletin_board);
    }

    // INFLATE MENU //
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.secondmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.toolbar_button3){
            //---//
        }
        else if(item.getItemId()==R.id.toolbar_button4){
            //---//
        }

        else if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
