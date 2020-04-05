package com.example.propertymaintenance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

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
    private ProgressDialog progressDialog;

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
        USER_LEVEL = SessionManagement.getUserLevelFromSharedPrefs();
        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.board_progress_dialog_fi));
        progressDialog.show();

        setToolbarTitle(getString(R.string.app_subtitle_bulletin_board));
        getBulletinBoardData(checkStakeHolder());
    }

    private String checkStakeHolder() {
        String url = "";
        if (USER_LEVEL == 0) {
            STAKEHOLDER_ID = SessionManagement.getUserHousingCooperativeIdFromSharedPrefs();
            url = getString(R.string.api_resident_bulletin_board);
        }
        else if (USER_LEVEL == 1) {
            STAKEHOLDER_ID = SessionManagement.getUserPropertyMaintenanceIDFromSharedPrefs();
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
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            if (jsonArray.length() < 1) {
                                titles.add(getString(R.string.board_is_empty_fi));
                                messages.add("");
                            }
                            else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject results = jsonArray.getJSONObject(i);
                                    titles.add(results.getString("Title"));
                                    messages.add(results.getString("Message"));
                                }
                            }
                            listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            titles.add(getString(R.string.error_server_fi));
                            messages.add(getString(R.string.error_ask_retry_fi));
                            listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                titles.add(getString(R.string.error_server_fi));
                messages.add(getString(R.string.error_ask_retry_fi));
                listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }
}
