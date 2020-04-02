package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDoneException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HousingInfo extends AppCompatActivity {

    private static final String TAG = "TAG" ;
    ListView lV;
    static final String[] INFO_TITLES = new String[]{"Pelastussuunnitelma", "Taloyhtion yhteystiedot",
            "Taloyhtion jarjestyssaannot", "Kiinteistohuollon yhteystiedot", "Isannoinnin yhteystiedot",
            "Jatehuollon yhteystiedot"};

    static final String RESCUE = "https://group3mobilebucket.s3.amazonaws.com/mockupdocuments/taloyhtion_pelastussuunnitelma.pdf";
    static final String REGULATION= "https://group3mobilebucket.s3.amazonaws.com/mockupdocuments/taloyhtion_jarjestyssaannot.pdf";

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

                   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(RESCUE)));
                }

                else if (position == 1) {
                    Intent intent = new Intent(HousingInfo.this, HousingContact.class);
                    startActivity(intent);
                }
                else if (position == 2) {
                    Intent intent = new Intent(HousingInfo.this, HousingRegulations.class);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(REGULATION)));
                }
                else if (position == 3) {
                    Intent intent = new Intent(HousingInfo.this, PropertyMaintenanceContact.class);
                    startActivity(intent);
                }
                else if (position == 4) {
                    Toast.makeText(HousingInfo.this, "Tästä voisit siirtyä isännöitsijän yhteystietoihin", Toast.LENGTH_SHORT).show();
                }
                else if (position == 5) {
                    Toast.makeText(HousingInfo.this, "Tästä voisit siirtyä jätehuollon yhteystietoihin", Toast.LENGTH_SHORT).show();
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



