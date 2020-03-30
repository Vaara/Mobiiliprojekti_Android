package com.example.propertymaintenance;

import android.content.Intent;
import android.os.Bundle;
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

public class CustodianServiceAdviceViewDetails extends BaseActivity {
    private TextView textView;
    private TextView textResident;
    private int checkID;
    private int residentId;
    static int serviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_custodian_service_advice_view_details;
    }

    @Override
    protected void doStuff() {
        textView = findViewById(R.id.testServiceAdvice);
        textResident = findViewById(R.id.testResident);
        RequestQueue queue = Volley.newRequestQueue(this);

        String urlBase = getString(R.string.api_base);
        String urlServiceAdvice = urlBase + getString(R.string.api_service_advice) + SessionManagement.getUserPropertyMaintenanceIDFromSharedPrefs();
        String urlResident;

        Intent getID = getIntent();
        serviceID = getID.getIntExtra("serviceID", -999);

        serviceAdvice(urlServiceAdvice);

        /*
        urlResident = "http://ec2-18-234-159-189.compute-1.amazonaws.com/resident/1";
        JsonObjectRequest getResidentInfo = new JsonObjectRequest(Request.Method.GET, urlResident, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Name = response.getString("Name");
                            String Phone = response.getString("Phone");

                            textResident.setText(Name + "\n" + Phone + "\n\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                textResident.setText(error.toString());
            }
        });
        //---------------------------------------------------------------------------


        queue.add(getResidentInfo);*/
    }


    private void serviceAdvice(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getServiceAdvice = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject ServiceAdvice = jsonArray.getJSONObject(i);
                                checkID = ServiceAdvice.getInt("idServiceAdvices");
                                if (checkID == serviceID) {
                                    residentId = ServiceAdvice.getInt("idResidents");
                                    String messageTitle = ServiceAdvice.getString("ServiceMessageTitle");
                                    String message = ServiceAdvice.getString("ServiceMessage");
                                    String additionalmessage = ServiceAdvice.getString("AdditionalMessage");

                                    String imageId = ServiceAdvice.getString("ImageId");
                                    String masterKey = ServiceAdvice.getString("MasterKeyAllowed");
                                    String contactResident = ServiceAdvice.getString("ContactResident");
                                    String isDone = ServiceAdvice.getString("Done");

                                    if (additionalmessage == null || additionalmessage == "null") {
                                        additionalmessage = "";
                                    }

                                    textView.setText(messageTitle + " " + "\n" + message + " " + "\n" + additionalmessage + "\n\n");
                                    //textView.setText(imageId + " " + "\n" + masterKey + " " + "\n" + contactResident + "\n\n");
                                } else {

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        textView.setText(error.toString());

                    }
                });
        queue.add(getServiceAdvice);
    }
}

        /*
        //----------------------------------------------------------------------
        String urlResident = "http://ec2-18-234-159-189.compute-1.amazonaws.com/resident/1";
        JsonObjectRequest getResidentInfo = new JsonObjectRequest(Request.Method.GET, urlResident, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String residentName = response.getString("Name");
                            String residentPhone = response.getString("Phone");

                            textResident.setText(residentName + "\n" + residentPhone + "\n\n");
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //textTemp.setText(error.toString());
            }
        });
        //---------------------------------------------------------------------------
        queue.add(getResidentInfo);

         */