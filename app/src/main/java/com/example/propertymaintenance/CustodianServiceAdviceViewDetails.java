package com.example.propertymaintenance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        textResident.setText(""+serviceID);

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

        //sendReport();
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



















































































    public void sendReport() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progress_dialog_sending_fi));
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-234-159-189.compute-1.amazonaws.com/serviceadvicereport";

        JSONObject reportBody = new JSONObject();
        try {
            reportBody.put("idServiceAdvices", serviceID);
            reportBody.put("idCustodians", SessionManagement.getUserIdFromSharedPrefs());
            reportBody.put("CustodianReport", "Kettingissä reikiä");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 201 created
        // 500 database error / incorrect values

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, reportBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray results = response.getJSONArray("results");
                            //Toast.makeText(CustodianServiceAdviceViewDetails.this, R.string.toast_report_sent_fi, Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(CustodianServiceAdviceViewDetails.this, R.string.toast_report_sent_fi, Toast.LENGTH_LONG).show();
                            Log.d("CustodianSendReport", "sendReport(): catch");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //Toast.makeText(CustodianServiceAdviceViewDetails.this, getString(R.string.error_server), Toast.LENGTH_LONG).show();
                        //Toast.makeText(CustodianServiceAdviceViewDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("CustodianSendReport", "sendReport(): onErrorResponse");
                    }
                }
        );
        queue.add(getRequest);
    }
}
