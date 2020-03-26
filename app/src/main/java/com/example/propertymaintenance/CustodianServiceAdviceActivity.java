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

public class CustodianServiceAdviceActivity extends BaseActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_custodian_service_advice;
    }

    @Override
    protected void doStuff() {
        setToolbarTitle("Vikailmoitukset"); // replaced the setupToolbar();

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCustodianServiceAdviceOpen) {
            btnOpen.setEnabled(false);
            btnDone.setEnabled(true);
            btnAll.setEnabled(true);
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
}
