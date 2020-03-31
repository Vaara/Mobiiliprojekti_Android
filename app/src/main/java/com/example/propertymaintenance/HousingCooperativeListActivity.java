package com.example.propertymaintenance;


import android.os.Bundle;

public class HousingCooperativeListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_housing_cooperative_list;
    }

    @Override
    protected void doStuff() {

        setToolbarTitle(getString(R.string.app_subtitle_housing_cooperative_list));
    }
}
