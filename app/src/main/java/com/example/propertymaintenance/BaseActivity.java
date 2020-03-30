package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar toolBar;
    TextView subtitle;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        toolBar = (Toolbar) this.findViewById(R.id.includeToolbar);
        subtitle = (TextView) findViewById(R.id.toolbar_subtitle);
        userName = (TextView) findViewById(R.id.toolbar_user);

        if (toolBar != null) {
            setSupportActionBar(toolBar);

            if (getSupportActionBar() != null) {

                if(getLayoutResource() != R.layout.activity_main)
                {
                    setDisplayHomeEnabled(true);
                }

                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);

            }
        }
        setToolbarTitle();
        setToolbarUser();
        doStuff();
    }

    protected abstract int getLayoutResource();  // In child @Override, ex. return R.layout.activity_main;
    protected abstract void doStuff();  // In child code that was in onCreate()

    public void setDisplayHomeEnabled(boolean b) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(b);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.toolbar_button1) {
            //ASETUKSET
            setDisplayHomeEnabled(false);
        }

        else if (item.getItemId() == R.id.toolbar_button2) {
            //KIRJAUDU ULOS
            logout(this);
        }

        else if(item.getItemId() == android.R.id.home) {
            //TOOLBAR <-
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setToolbarTitle(){
        if(SessionManagement.getUserLevelFromSharedPrefs() == 1 && subtitle.getText() != "kiinteistöhuolto")
        {
            subtitle.setText("Kiinteistöhuolto");
        }
        else
        {
            subtitle.setText("Asukasportaali");
        }
    }
    protected void setToolbarTitle(String title){
            subtitle.setText(title);
    }

    protected void setToolbarUser(){
            userName.setText(""+SessionManagement.getUserFullNameFromSharedPrefs());
    }

    public void logout(Activity activity) {
        SessionManagement sessionManagement = new SessionManagement(activity);
        sessionManagement.removeSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}


