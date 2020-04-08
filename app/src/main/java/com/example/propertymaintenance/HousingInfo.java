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

public class HousingInfo extends BaseActivity {

    private static final String TAG = "TAG" ;
    ListView lV;
    static final String[] INFO_TITLES = new String[]{"Pelastussuunnitelma", "Taloyhtiön järjestyssäännöt",
            "Taloyhtiön yhteystiedot", "Kiinteistöhuollon yhteystiedot", "Isännoinnin yhteystiedot",
            "Jätehuollon yhteystiedot"};

    static final String RESCUE = "https://group3mobilebucket.s3.amazonaws.com/mockupdocuments/taloyhtion_pelastussuunnitelma.pdf";
    static final String REGULATION= "https://group3mobilebucket.s3.amazonaws.com/mockupdocuments/taloyhtion_jarjestyssaannot.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_housing_info;
    }

    @Override
    protected void doStuff() {
        lV = findViewById(R.id.housingInfoList);
        lV.setAdapter(new InfoAdapter(this, INFO_TITLES));
        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(RESCUE)));
                }

                else if (position == 1) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(REGULATION)));
                }
                else if (position == 2) {
                    Intent intent = new Intent(HousingInfo.this, HousingContact.class);
                    startActivity(intent);

                }
                else if (position == 3) {
                    Intent intent = new Intent(HousingInfo.this, PropertyMaintenanceContact.class);
                    startActivity(intent);
                }
                else if (position == 4) {
                    Intent intent = new Intent(HousingInfo.this, PropertyManagement.class);
                    startActivity(intent);

                }
                else if (position == 5) {
                    Toast.makeText(HousingInfo.this, "Tästä voisit siirtyä jätehuollon yhteystietoihin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setToolbarTitle("Taloyhtiö info");
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
                    imageView.setImageResource(R.drawable.ic_icon_bubble_emergency);
                }
                else if (icon.equals("Taloyhtiön yhteystiedot")){
                    imageView.setImageResource(R.drawable.ic_icon_contacthousing);
                }
                else if (icon.equals("Taloyhtiön järjestyssäännöt")){
                    imageView.setImageResource(R.drawable.ic_icon_bubble_handshake);
                }
                else if (icon.equals("Kiinteistöhuollon yhteystiedot")){
                    imageView.setImageResource(R.drawable.ic_icon_contactmaintenance);
                }
                else if (icon.equals("Isännoinnin yhteystiedot")){
                    imageView.setImageResource(R.drawable.ic_icon_contactproperty);
                }
                else if (icon.equals("Jätehuollon yhteystiedot")){
                    imageView.setImageResource(R.drawable.ic_icon_contactwaste);
                }
                else {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
            else {
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
}



