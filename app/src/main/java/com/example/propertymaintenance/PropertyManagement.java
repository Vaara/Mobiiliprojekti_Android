package com.example.propertymaintenance;


import android.os.Bundle;
import android.view.WindowManager;

public class PropertyManagement extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() { return R.layout.activity_property_management; }

    @Override
    protected void doStuff() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setToolbarTitle("Isännöinnin yhteystiedot");
    }
}
