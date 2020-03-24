package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CustodianServiceAdviceActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOpen;
    Button btnDone;
    Button btnAll;
    ListView listView;
    CustodianServiceAdviceAdapter adapter;

    static final String[] messageTitles = new String[] {
            "Pihavalo palanut", "Aita kaatunut", "Lumikola hävinnyt", "Postilaatikko rikki", "Roskakatos epäsiisti"
    };

    static final String[] housingCooperatives = new String[] {
            "As Oy Lamppu", "As Oy Heinäpää", "As Oy Talvipuisto", "As Oy Ketunlenkki", "As Oy Rantakehä"
    };

    // Fetch serviceAdvices from database and sort to showcurrent, all, open and done

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custodian_service_advice);

        setupToolbar();

        listView = findViewById(R.id.lvCustodianServiceAdvice);
        adapter = new CustodianServiceAdviceAdapter(this, messageTitles, housingCooperatives);
        listView.setAdapter(adapter);

        btnOpen = findViewById(R.id.btnCustodianServiceAdviceOpen);
        btnOpen.setOnClickListener(this);
        btnDone = findViewById(R.id.btnCustodianServiceAdviceDone);
        btnDone.setOnClickListener(this);
        btnAll = findViewById(R.id.btnCustodianServiceAdviceAll);
        btnAll.setOnClickListener(this);
        btnOpen.setEnabled(false);
        btnDone.setEnabled(true);
        btnAll.setEnabled(true);
    }

    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.include2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView subtitle = toolbar.findViewById(R.id.toolbar_subtitle);
        subtitle.setText("Vikailmoitukset");
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
            logout();
        }

        else if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCustodianServiceAdviceOpen) {
            btnOpen.setEnabled(false);
            btnDone.setEnabled(true);
            btnAll.setEnabled(true);
            // show proper list
            // notifydatasetChanged
        }

        else if (v.getId() == R.id.btnCustodianServiceAdviceDone) {
            btnOpen.setEnabled(true);
            btnDone.setEnabled(false);
            btnAll.setEnabled(true);
        }

        else if (v.getId() == R.id.btnCustodianServiceAdviceAll) {
            btnOpen.setEnabled(true);
            btnDone.setEnabled(true);
            btnAll.setEnabled(false);
        }
    }

    public void logout() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progress_dialog_logout_fi));
        progressDialog.show();
        //Toast.makeText(MainActivity.this, R.string.toast_logout_fi,Toast.LENGTH_LONG).show();
        SessionManagement sessionManagement = new SessionManagement(this);
        sessionManagement.removeSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
