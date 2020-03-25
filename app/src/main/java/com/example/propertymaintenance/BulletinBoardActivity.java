package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BulletinBoardActivity extends AppCompatActivity {
    private ListView listViewBulletinBoard;
    private RequestQueue requestQueue;
    private ArrayList<String> titles;
    private ArrayList<String> messages;
    private BulletinBoardAdapter bulletinBoardAdapter;

    private int USER_ID;
    private int USER_LEVEL;
    private int STAKEHOLDER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        listViewBulletinBoard = findViewById(R.id.listViewBulletinBoard);
        titles = new ArrayList<>();
        messages = new ArrayList<>();
        bulletinBoardAdapter = new BulletinBoardAdapter(this, titles, messages);

        requestQueue = Volley.newRequestQueue(this);
        USER_ID = new SessionManagement(this).getUserIdFromSharedPrefs();
        USER_LEVEL = new SessionManagement(this).getUserLevelFromSharedPrefs();

        setupToolbar();
        getBulletinBoardData(checkStakeHolder());
    }

    private String checkStakeHolder() {
        String url = "";
        if (USER_LEVEL == 0) {
            STAKEHOLDER_ID = new SessionManagement(this).getUserHousingCooperativeIdFromSharedPrefs();
            url = getString(R.string.api_resident_bulletin_board);
        }
        else if (USER_LEVEL == 1) {
            STAKEHOLDER_ID = new SessionManagement(this).getUserPropertyMaintenanceIDFromSharedPrefs();
            url = getString(R.string.api_custodian_bulletin_board);
        }
        STAKEHOLDER_ID = 5;
        return url;
    }

    private void getBulletinBoardData(String stakeholderUrl) {
        String url = getString(R.string.api_base) + stakeholderUrl + STAKEHOLDER_ID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject results = jsonArray.getJSONObject(i);
                                titles.add(results.getString("Title"));
                                messages.add(results.getString("Message"));
                                String title = results.getString("Title");
                                String message = results.getString("Message");
                            }
                            listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                        } catch (JSONException e) {
                            Log.d("Error", "error1");
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "error2");
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.include3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView subtitle = (TextView) toolbar.findViewById(R.id.toolbar_subtitle);
        subtitle.setText(R.string.app_subtitle_bulletin_board);
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
