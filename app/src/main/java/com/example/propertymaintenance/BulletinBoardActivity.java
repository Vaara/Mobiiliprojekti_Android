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

public class BulletinBoardActivity extends BaseActivity {
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
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_bulletin_board;
    }

    @Override
    protected void doStuff() {
        listViewBulletinBoard = findViewById(R.id.listViewBulletinBoard);
        titles = new ArrayList<>();
        messages = new ArrayList<>();
        bulletinBoardAdapter = new BulletinBoardAdapter(this, titles, messages);

        requestQueue = Volley.newRequestQueue(this);
        USER_ID = new SessionManagement(this).getUserIdFromSharedPrefs();
        USER_LEVEL = new SessionManagement(this).getUserLevelFromSharedPrefs();

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
                            }
                            listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                        } catch (JSONException e) {
                            titles.add(getString(R.string.error_server));
                            messages.add(getString(R.string.error_ask_retry));
                            listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                            Log.d("Error", "error1");
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                titles.add(getString(R.string.error_server));
                messages.add(getString(R.string.error_ask_retry));
                listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                Log.d("Error", "error2");
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }
}