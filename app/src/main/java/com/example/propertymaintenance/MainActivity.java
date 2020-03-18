package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    static final String[] BUTTONLABELS = new String[] {
            "Taloyhtiö info", "Vikailmoitus","Ilmoitustaulu", "Varaukset" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview1);
        gridView.setAdapter(new ImageAdapter(this, BUTTONLABELS));

    }


    // TOOLBAR //
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
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
    // TOOLBAR //








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

                gridView = new View(context);

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


                if (position == 0)
                {
                    imageView.setImageResource(R.drawable.taloyhtio);
                }
                else if (position == 1)
                {
                    imageView.setImageResource(R.drawable.vikailmoitus);
                }
                else if(position == 2)
                {
                    imageView.setImageResource(R.drawable.ilmoitus);
                }
                else if(position == 3)
                {
                    imageView.setImageResource(R.drawable.kalenteri);
                }
                else
                {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }

                /*
                if (mobile.equals("Taloyhtiö info")) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else if (mobile.equals("Vikailmoitus")) {
                    imageView.setImageResource(R.mipmap.ic_launcher_round);
                } else if (mobile.equals("Ilmoitustaulu")) {
                    imageView.setImageResource(R.mipmap.ic_launcher_round);
                } else {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }

                 */

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

