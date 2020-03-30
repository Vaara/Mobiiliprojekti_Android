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

public class HousingInfo extends BaseActivity {

    ListView lV;
    static final String[] INFO_TITLES = new String[]{"Pelastussuunnitelma", "Taloyhtiön järjestyssäännöt",
            "Taloyhtiön yhteystiedot", "Kiinteistöhuollon yhteystiedot", "Isännoinnin yhteystiedot",
            "Jätehuollon yhteystiedot"};

    String URL = "https://group3mobilebucket.s3.amazonaws.com/taloyhtion_pelastussuunnitelma.pdf";
    String URL_2 = "https://group3mobilebucket.s3.amazonaws.com/taloyhtion_jarjestyssaannot.pdf";

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
                    Intent intent = new Intent(HousingInfo.this, RescuePlan.class);
                    startActivity(intent);
                }

                else if (position == 1) {
                    Intent intent = new Intent(HousingInfo.this, HousingRegulations.class);
                    startActivity(intent);
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
                    imageView.setImageResource(R.drawable.android_logo);
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



