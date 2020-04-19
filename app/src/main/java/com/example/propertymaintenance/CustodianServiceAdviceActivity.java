package com.example.propertymaintenance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

    private Button btnOpen;
    private Button btnDone;
    private ListView listView;
    private ProgressDialog progressDialog;
    private CustodianServiceAdviceAdapter adapterId;
    private CustodianServiceAdviceAdapter adapterName;

    static private ArrayList<String> messageTitles;
    static private ArrayList<String> housingCooperativeIds;
    static private ArrayList<String> housingCooperativeNames;
    static private ArrayList<Integer> idServiceAdviceOpen = new ArrayList<>();
    static private ArrayList<Integer> idServiceAdviceClosed = new ArrayList<>();

    static private HashMap<Integer, String> housingCooperativeIdsNames;

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
        housingCooperativeIds = new ArrayList<>();
        housingCooperativeNames = new ArrayList<>();

        housingCooperativeIdsNames = new HashMap<>();

        listView = findViewById(R.id.lvCustodianServiceAdvice);
        adapterId = new CustodianServiceAdviceAdapter(this, messageTitles, housingCooperativeIds);
        listView.setAdapter(adapterId);

        btnOpen = findViewById(R.id.btnCustodianServiceAdviceOpen);
        btnOpen.setOnClickListener(this);
        btnDone = findViewById(R.id.btnCustodianServiceAdviceDone);
        btnDone.setOnClickListener(this);
        btnOpen.setEnabled(false);
        btnDone.setEnabled(true);

        progressDialog = new ProgressDialog(CustodianServiceAdviceActivity.this);
        progressDialog.setMessage(getString(R.string.progress_dialog_loading_fi));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CustodianServiceAdviceViewDetails.class);

                if(!btnOpen.isEnabled()) {
                    intent.putExtra("serviceID", idServiceAdviceOpen.get(position));
                    intent.putExtra("done", 0);
                }
                else {
                    intent.putExtra("serviceID", idServiceAdviceClosed.get(position));
                    intent.putExtra("done", 1);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlBasePart = "http://ec2-18-234-159-189.compute-1.amazonaws.com/serviceadvice/";
        String urlIdPart = SessionManagement.getUserPropertyMaintenanceIDFromSharedPrefs().toString();
        String url = urlBasePart + urlIdPart;

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        clearLists();

                        try {
                            JSONArray results = response.getJSONArray("results");

                            if (results.length() < 1) {
                                messageTitles.add(getString(R.string.custodian_service_advice_no_service_advices_fi));
                                housingCooperativeIds.add("");
                                adapterId.notifyDataSetChanged();
                            }
                            else {
                                for (int i=0; i<results.length(); i++) {
                                    JSONObject singleServiceAdvice = results.getJSONObject(i);
                                    String title = singleServiceAdvice.optString("ServiceMessageTitle");
                                    Integer housingCooperativeId = singleServiceAdvice.optInt("idHousingCooperative");
                                    Integer done = singleServiceAdvice.optInt("Done");
                                    String nameId = housingCooperativeId.toString();

                                    housingCooperativeIdsNames.put(housingCooperativeId, nameId);

                                    // Open ServiceAdvices selected
                                    if (!btnOpen.isEnabled()) {
                                        if (done == 0) {
                                            messageTitles.add(title);
                                            housingCooperativeIds.add(nameId);
                                            idServiceAdviceOpen.add(singleServiceAdvice.getInt("idServiceAdvices"));
                                        }
                                    }

                                    // Done ServiceAdvices selected
                                    else if (!btnDone.isEnabled()) {
                                        if (done == 1) {
                                            messageTitles.add(title);
                                            housingCooperativeIds.add(nameId);
                                            idServiceAdviceClosed.add(singleServiceAdvice.getInt("idServiceAdvices"));
                                        }
                                    }
                                }
                                fetchHousingCooperativeNames();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CustodianServiceAdviceActivity.this, R.string.something_went_wrong_fi, Toast.LENGTH_LONG).show();
                            Log.d("CustodianServiceAdvice", "fetchData(): catch");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CustodianServiceAdviceActivity.this, getString(R.string.error_server_fi), Toast.LENGTH_LONG).show();
                        Log.d("CustodianServiceAdvice", "fetchData(): onErrorResponse");
                    }
                }
        );
        queue.add(getRequest);
    }

    public void fetchHousingCooperativeNames() {
        RequestQueue queueName = Volley.newRequestQueue(this);
        String url = "http://ec2-18-234-159-189.compute-1.amazonaws.com/housingcooperative";

        JsonObjectRequest getRequestName = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Integer id;
                        String name;

                        try {
                            JSONArray results = response.getJSONArray("results");

                            if (results.length() < 1) {
                                id = -1;
                                name = "Name not found";
                                housingCooperativeIdsNames.put(id, name);
                            }
                            else {
                                for (int i=0; i<results.length(); i++) {
                                    JSONObject singleHousingCooperative = results.getJSONObject(i);
                                    id = singleHousingCooperative.optInt("idHousingCooperative");
                                    name = singleHousingCooperative.optString("Name");
                                    housingCooperativeIdsNames.put(id, name);
                                }
                            }
                            updateIdsToNames();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If fetchHousingCooperativeNames() fails, use ids instead of names
                        adapterId.notifyDataSetChanged();
                        progressDialog.dismiss();
                        Log.d("CustodianServiceAdvice", "fetchHousingCooperativeNames(): onErrorResponse");
                    }
                }
        );
        queueName.add(getRequestName);
    }

    public void updateIdsToNames() {

        for (int i = 0; i< housingCooperativeIds.size(); i++) {
            try {
                Integer id = Integer.parseInt(housingCooperativeIds.get(i));
                String name = housingCooperativeIdsNames.get(id);
                housingCooperativeNames.add(name);
            } catch (Exception e) {
                Log.d("CustodianServiceAdvice", "updateIdsToNames(): catch");
            }
        }
        adapterName = new CustodianServiceAdviceAdapter(this, messageTitles, housingCooperativeNames);
        listView.setAdapter(adapterName);
        progressDialog.dismiss();
    }

    public void clearLists() {
        messageTitles.clear();
        housingCooperativeIds.clear();
        housingCooperativeNames.clear();
        housingCooperativeIdsNames.clear();
    }
}
