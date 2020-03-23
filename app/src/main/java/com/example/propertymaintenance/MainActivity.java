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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Vibrator;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    private Vibrator hapticFeedback;

    static final int HOUSING_ID = 666;
    static final int FIX_ID = 777;
    static final int BULLETIN_ID = 888;
    static final int CALENDAR_ID = 999;

    static final String[] BUTTONLABELS = new String[] {
            "Taloyhtiö info", "Vikailmoitus","Ilmoitustaulu", "Varaukset" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        hapticFeedback = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        gridView = (GridView) findViewById(R.id.gridview1);
        gridView.setAdapter(new ImageAdapter(this, BUTTONLABELS));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                switch (position){
                    case 0:
                        hapticFeedback.vibrate(50);
                        Intent intentHousing = new Intent(getBaseContext(), intentTest.class);
                        startActivityForResult(intentHousing, HOUSING_ID);
                            break;
                    case 1:
                        hapticFeedback.vibrate(50);
                        Intent intentFix = new Intent(getBaseContext(), intentTest.class);
                        startActivityForResult(intentFix, FIX_ID);
                            break;
                    case 2:
                        hapticFeedback.vibrate(50);
                        Intent intentBulletin = new Intent(getBaseContext(), intentTest.class);
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


    // set ToolBar //
    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.include1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    // INFLATE MENU //
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.toolbar_button1){
            //---//
        }
        else if(item.getItemId()==R.id.toolbar_button2){
            //---//
        }
        return super.onOptionsItemSelected(item);
    }


    //IMAGE ADAPTER//
    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private final String[] mobileValues;

        public ImageAdapter(Context context, String[] mobileValues) {
            this.context = context;
            this.mobileValues = mobileValues;
        }

        //---returns an ImageView view---
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


                switch (position){
                    case 0:
                        imageView.setImageResource(R.drawable.ic_icon_fix);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.ic_icon_clipboard);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.ic_icon_checklist);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.ic_icon_postit);
                        break;
                    default:
                        imageView.setImageResource(R.mipmap.ic_launcher);
                        break;
                }

            } else {
                gridView = (View) convertView;
            }

            return gridView;
        }

        //---returns the number of images---
        @Override
        public int getCount() {
            return mobileValues.length;
        }

        //---returns the item---
        @Override
        public Object getItem(int position) {
            return null;
        }

        //---returns the ID of an item---
        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}

