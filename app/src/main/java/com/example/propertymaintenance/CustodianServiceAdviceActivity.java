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
import java.util.HashMap;

public class CustodianServiceAdviceActivity extends BaseActivity implements View.OnClickListener {

    Button btnOpen;
    Button btnDone;
    ListView listView;
    CustodianServiceAdviceAdapter adapter;

    static private ArrayList<String> messageTitles;
    static private ArrayList<String> housingCooperatives;

    static HashMap<Integer, String> housingCooperativeIds;

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
        setToolbarTitle("Vikailmoitukset");

        messageTitles = new ArrayList<>();
        housingCooperatives = new ArrayList<>();

        housingCooperativeIds = new HashMap<>();

        listView = findViewById(R.id.lvCustodianServiceAdvice);
        adapter = new CustodianServiceAdviceAdapter(this, messageTitles, housingCooperatives);
        listView.setAdapter(adapter);

        btnOpen = findViewById(R.id.btnCustodianServiceAdviceOpen);
        btnOpen.setOnClickListener(this);
        btnDone = findViewById(R.id.btnCustodianServiceAdviceDone);
        btnDone.setOnClickListener(this);
        btnOpen.setEnabled(false);
        btnDone.setEnabled(true);

        fetchData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCustodianServiceAdviceOpen) {
            btnOpen.setEnabled(false);
            btnDone.setEnabled(true);
            fetchData();
        }

        else if (v.getId() == R.id.btnCustodianServiceAdviceDone) {
            btnOpen.setEnabled(true);
            btnDone.setEnabled(false);
            fetchData();
        }
    }

    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progress_dialog_loading_fi));
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlBasePart = "http://ec2-18-234-159-189.compute-1.amazonaws.com/serviceadvice/";
        String urlIdPart = SessionManagement.getUserPropertyMaintenanceIDFromSharedPrefs().toString();
        String url = urlBasePart + urlIdPart;

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        messageTitles.clear();
                        housingCooperatives.clear();
                        //housingCooperativeIds.clear();

                        try {
                            JSONArray results = response.getJSONArray("results");

                            if (results.length() < 1) {
                                messageTitles.add(getString(R.string.custodian_service_advice_no_service_advices_fi));
                                housingCooperatives.add("");
                            }

                            else {
                                for (int i=0; i<results.length(); i++) {
                                    JSONObject singleServiceAdvice = results.getJSONObject(i);
                                    String title = singleServiceAdvice.optString("ServiceMessageTitle");
                                    Integer housingCooperativeId = singleServiceAdvice.optInt("idHousingCooperative");
                                    Integer done = singleServiceAdvice.optInt("Done");
                                    String name;


                                    if (housingCooperativeIds.containsKey(housingCooperativeId)) {
                                        name = housingCooperativeIds.get(housingCooperativeId);
                                        housingCooperatives.add(name);
                                    }

                                    else {
                                        name = "Taloyhtiö";

                                        //messageTitles.add(title);
                                        //housingCooperatives.add(name);

                                        // Somehow prevents all messages to download to list
                                        // housingCooperativeIds.put(housingCooperativeId, name);


                                        if (btnOpen.isEnabled() == false) {

                                            if (done ==0) {
                                                messageTitles.add(title);
                                                housingCooperatives.add(name);
                                                //housingCooperativeIds.put(id, name);
                                            }
                                        }

                                        else if (btnDone.isEnabled() == false) {

                                            if (done == 1) {
                                                messageTitles.add(title);
                                                housingCooperatives.add(name);
                                                //housingCooperativeIds.put(id, name);
                                            }
                                        }

                                        //fetchName(id);
                                        Log.d("TEST", "fetchData() Name from hashmap " + name);
                                        Log.d("TEST", "fetchData() name " + name);
                                    }
                                }
                                Log.d("TEST", "fetchData()");
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CustodianServiceAdviceActivity.this, R.string.something_went_wrong_fi, Toast.LENGTH_LONG).show();
                            Log.d("CustodianServiceAdvice", "catch in CustodianServiceAdviceRequestResponse");
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

    public void fetchName(final Integer idTest) {
        RequestQueue queueName = Volley.newRequestQueue(this);
        String urlBasePart = "http://ec2-18-234-159-189.compute-1.amazonaws.com/housingcooperative/";
        String urlIdPart = idTest.toString();
        String url = urlBasePart + urlIdPart;

        JsonObjectRequest getRequestName = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String name;

                        try {
                            JSONArray results = response.getJSONArray("results");

                            if (results.length() < 1) {
                                name = "Name not found";
                            }

                            else {
                                JSONObject singleServiceAdvice = results.getJSONObject(0);
                                name = singleServiceAdvice.optString("Name");
                                housingCooperativeIds.put(idTest, name);
                                String nema = housingCooperativeIds.get(idTest);
                                Log.d("TEST", "fetchName()");
                                Log.d("TEST", "fetchName() Name from optstring " + name);
                                Log.d("TEST", "fetchName() Name from hashmap " + nema);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TEST", "onErrorResponse fetchName()");
                    }
                }
        );
        queueName.add(getRequestName);
    }
}
