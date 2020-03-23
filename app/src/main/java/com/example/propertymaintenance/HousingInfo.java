package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HousingInfo extends AppCompatActivity {

    ListView lV;
    static final String[] INFO_TITLES = new String[]{"Pelastussuunnitelma", "Taloyhtion yhteystiedot",
            "Taloyhtion jarjestyssaannot", "Kiinteistohuollon yhteystiedot", "Isannoinnin yhteystiedot",
            "Jatehuollon yhteystiedot"};

    String URL = "https://group3mobilebucket.s3.amazonaws.com/taloyhtion_pelastussuunnitelma.pdf";
    String URL_2 = "https://group3mobilebucket.s3.amazonaws.com/taloyhtion_jarjestyssaannot.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing_info);

        setupToolbar();

        lV = findViewById(R.id.housingInfoList);

        lV.setAdapter(new InfoAdapter(this, INFO_TITLES));

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent intent = new Intent(HousingInfo.this, RescuePlan.class);
                }

                if (position == 1) {
                    Intent intent = new Intent(HousingInfo.this, HousingContact.class);
                    startActivity(intent);
                }
                if (position == 2) {
                    Intent intent = new Intent(HousingInfo.this, HousingRegulations.class);
                    startActivity(intent);
                }
                if (position == 3) {
                    Intent intent = new Intent(HousingInfo.this, PropertyMaintenanceContact.class);
                    startActivity(intent);
                }
                if (position == 4) {
                    Intent intent = new Intent(HousingInfo.this, PropertyManagement.class);
                    startActivity(intent);
                }
                if (position == 5) {
                    Intent intent = new Intent(HousingInfo.this, WasteManagement.class);
                    startActivity(intent);
                }
            }
        });
    }

    public class InfoAdapter extends BaseAdapter {
        private Context contex;
        private final String[] infoValues;

        public InfoAdapter(Context context, String[] infoVAlues) {
            this.contex = context;
            this.infoValues = infoVAlues;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View listView;

            if (convertView == null) {

                listView = new View(contex);

                listView = inflater.inflate(R.layout.info_design, null);

                TextView textView = (TextView) listView.findViewById(R.id.listText);
                textView.setText(infoValues[position]);
                ImageView imageView = (ImageView) listView.findViewById(R.id.listIcon);

                String icon = infoValues[position];

                if (icon.equals("Pelastussuunnitelma")){
                    imageView.setImageResource(R.drawable.android_logo);
                } else if (icon.equals("Taloyhtiohallituksen tiedot")){
                    imageView.setImageResource(R.drawable.android_logo);
                } else if (icon.equals("Taloyhtion yhteystiedot")){
                    imageView.setImageResource(R.drawable.android_logo);
                } else if (icon.equals("Kiinteistohuollon yhteystiedot")){
                    imageView.setImageResource(R.drawable.android_logo);
                } else if (icon.equals("Isannoinnin yhteystiedot")){
                    imageView.setImageResource(R.drawable.android_logo);
                } else {
                    imageView.setImageResource(R.drawable.android_logo);
                }
            } else {

                listView = (View) convertView;
            }

            return listView;
        }

        @Override
        public int getCount(){
            return infoValues.length;
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }
    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.include2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView subtitle = (TextView) toolbar.findViewById(R.id.toolbar_subtitle);
        subtitle.setText("Näkymän nimi");
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



