package com.example.propertymaintenance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CustodianServiceAdviceActivity extends BaseActivity implements View.OnClickListener {

    Button btnOpen;
    Button btnDone;
    Button btnAll;
    ListView listView;
    CustodianServiceAdviceAdapter adapter;

    private ArrayList<String> messageTitles;
    private ArrayList<String> housingCooperatives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_custodian_service_advice;
    }

    @Override
    protected void doStuff() {
        setToolbarTitle("Vikailmoitukset"); // replaced the setupToolbar();
        messageTitles = new ArrayList<>();
        housingCooperatives = new ArrayList<>();

        listView = findViewById(R.id.lvCustodianServiceAdvice);
        adapter = new CustodianServiceAdviceAdapter(this, messageTitles, housingCooperatives);
        listView.setAdapter(adapter);

        btnOpen = findViewById(R.id.btnCustodianServiceAdviceOpen);
        btnOpen.setOnClickListener(this);
        btnDone = findViewById(R.id.btnCustodianServiceAdviceDone);
        btnDone.setOnClickListener(this);
        btnAll = findViewById(R.id.btnCustodianServiceAdviceAll);
        btnAll.setOnClickListener(this);
        btnOpen.setEnabled(false);
        btnDone.setEnabled(true);
        btnAll.setEnabled(true);

        fetchData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCustodianServiceAdviceOpen) {
            btnOpen.setEnabled(false);
            btnDone.setEnabled(true);
            btnAll.setEnabled(true);
            fetchData();
        }

        else if (v.getId() == R.id.btnCustodianServiceAdviceDone) {
            btnOpen.setEnabled(true);
            btnDone.setEnabled(false);
            btnAll.setEnabled(true);
            fetchData();
        }

        else if (v.getId() == R.id.btnCustodianServiceAdviceAll) {
            btnOpen.setEnabled(true);
            btnDone.setEnabled(true);
            btnAll.setEnabled(false);
            fetchData();
        }
    }

    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Ladataan...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://ec2-18-234-159-189.compute-1.amazonaws.com/serviceadvice/1";

        /*
        JSONObject loginCredentials = new JSONObject();
        try {
            loginCredentials.put("username", edUsername.getText().toString());
            loginCredentials.put("password", edPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
         */

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        messageTitles.clear();
                        housingCooperatives.clear();
                        
                        try {
                            JSONArray results = response.getJSONArray("results");

                            if (results.length() < 1) {
                                messageTitles.add(getString(R.string.custodian_service_advice_no_service_advices_fi));
                                housingCooperatives.add("");
                            }

                            else {
                                for (int i=0; i<results.length(); i++) {
                                    JSONObject singleServiceAdvice = results.getJSONObject(i);
                                    messageTitles.add(singleServiceAdvice.optString("ServiceMessageTitle"));
                                    housingCooperatives.add(singleServiceAdvice.optString("ServiceMessage"));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            //Toast.makeText(CustodianServiceAdviceActivity.this, "Ollaan oikeessa paikassa", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CustodianServiceAdviceActivity.this, R.string.something_went_wrong_fi, Toast.LENGTH_LONG).show();
                            Log.d("CustodianServiceAdvice", "catch in CustodianServiceAdviceRequest");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CustodianServiceAdviceActivity.this, getString(R.string.error_server), Toast.LENGTH_LONG).show();
                        Log.d("CustodianServiceAdvice", "Error in CustodianServiceAdviceRequest");
                    }
                }
        );
        queue.add(getRequest);
    }
}
