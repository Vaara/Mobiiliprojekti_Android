package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class intentTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_intent_test;
    }

    @Override
    protected void doStuff() {
        //--
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toolbar_button3) {
            //---//
        } else if (item.getItemId() == R.id.toolbar_button4) {
            //---//
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    // set ToolBar //
    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.includeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView subtitle = (TextView) toolbar.findViewById(R.id.toolbar_subtitle);
        subtitle.setText("123");
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

 */
}