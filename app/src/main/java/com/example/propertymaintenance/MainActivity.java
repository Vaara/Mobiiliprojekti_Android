package com.example.propertymaintenance;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Vibrator;

public class MainActivity extends BaseActivity {

    GridView gridView;
    private Vibrator hapticFeedback;

    static final int HOUSING_ID = 666;
    static final int FIX_ID = 777;
    static final int BULLETIN_ID = 888;
    static final int CALENDAR_ID = 999;

    static final String[] BUTTONLABELSTENANT = new String[]{
            "Taloyhtiö info", "Vikailmoitus", "Ilmoitustaulu", "Varaukset"};

    static final String[] BUTTONLABELSMANAGEMENT = new String[]{
            "Omat taloyhtiöt", "Vikailmoitukset", "Ilmoitustaulu", "Työvuorot"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void doStuff() {

        hapticFeedback = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        gridView = (GridView) findViewById(R.id.gridview1);


        if(SessionManagement.getUserLevelFromSharedPrefs() == 1) {
            gridviewManagement();
        }
        else {
            gridviewTenant();
        }
    }

    private void gridviewManagement(){
        gridView.setAdapter(new ImageAdapter(this, BUTTONLABELSMANAGEMENT));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        /*
                        hapticFeedback.vibrate(50);
                        Intent intentHousing = new Intent(getBaseContext(), HousingInfo.class);
                        startActivityForResult(intentHousing, HOUSING_ID);
                         */
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void gridviewTenant(){
        gridView.setAdapter(new ImageAdapter(this, BUTTONLABELSTENANT));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        hapticFeedback.vibrate(50);
                        Intent intentHousing = new Intent(getBaseContext(), HousingInfo.class);
                        startActivityForResult(intentHousing, HOUSING_ID);
                        break;
                    case 1:
                        hapticFeedback.vibrate(50);
                        Intent intentFix = new Intent(getBaseContext(), CustodianServiceAdviceActivity.class);
                        startActivityForResult(intentFix, FIX_ID);
                        break;
                    case 2:
                        hapticFeedback.vibrate(50);
                        Intent intentBulletin = new Intent(getBaseContext(), BulletinBoardActivity.class);
                        startActivityForResult(intentBulletin, BULLETIN_ID);
                        break;
                    case 3:
                        hapticFeedback.vibrate(50);
                        Intent intentCalendar = new Intent(getBaseContext(), intentTest.class);
                        startActivityForResult(intentCalendar, CALENDAR_ID);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private final String[] mobileValues;

        public ImageAdapter(Context context, String[] mobileValues) {
            this.context = context;
            this.mobileValues = mobileValues;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                //gridView = new View(context); /// ??? tarvitaanko?

                // get layout from customgridview.xml
                gridView = inflater.inflate(R.layout.customgridview, null);

                // set value into textview
                TextView textView = (TextView) gridView
                        .findViewById(R.id.grid_icon_description);
                textView.setText(mobileValues[position]);

                // set image based on selected text
                ImageView imageView = (ImageView) gridView
                        .findViewById(R.id.grid_icon);

                String mobile = mobileValues[position];

                if(SessionManagement.getUserLevelFromSharedPrefs() == 1) {
                    switch (position) {
                        case 0:
                            imageView.setImageResource(R.drawable.ic_icon_housingcompanies);
                            break;
                        case 1:
                            imageView.setImageResource(R.drawable.ic_icon_fix);
                            break;
                        case 2:
                            imageView.setImageResource(R.drawable.ic_icon_postit);
                            break;
                        case 3:
                            imageView.setImageResource(R.drawable.ic_icon_clockin);
                            break;
                        default:
                            imageView.setImageResource(R.mipmap.ic_launcher);
                            break;
                    }
                }

                else {
                    switch (position) {
                        case 0:
                            imageView.setImageResource(R.drawable.ic_icon_housing);
                            break;
                        case 1:
                            imageView.setImageResource(R.drawable.ic_icon_fix);
                            break;
                        case 2:
                            imageView.setImageResource(R.drawable.ic_icon_postit);
                            break;
                        case 3:
                            imageView.setImageResource(R.drawable.ic_icon_calendar);
                            break;
                        default:
                            imageView.setImageResource(R.mipmap.ic_launcher);
                            break;
                    }
                }

            } else {
                gridView = (View) convertView;
            }

            return gridView;
        }

        @Override
        public int getCount() {
            return mobileValues.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}