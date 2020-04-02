package com.example.propertymaintenance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
        textResident.setText("" + serviceID);

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

        sendReport();
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
            reportBody.put("idCustodian", SessionManagement.getUserIdFromSharedPrefs());
            reportBody.put("CustodianReport", "Ovi korjattu");

            final String requestBody = reportBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("201")) {
                        Toast.makeText(CustodianServiceAdviceViewDetails.this,
                                R.string.toast_report_sent_fi, Toast.LENGTH_LONG).show();
                    }

                    else if (response.equalsIgnoreCase("500")){
                        Toast.makeText(CustodianServiceAdviceViewDetails.this,
                                R.string.error_server, Toast.LENGTH_LONG).show();
                    }
                    Log.d("ServiceAdviceReport", "onResponse()" + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(CustodianServiceAdviceViewDetails.this,
                            getString(R.string.error_server), Toast.LENGTH_LONG).show();
                    Log.d("ServiceAdviceReport", "onErrorResponse()" + error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf(
                                "Unsupported Encoding while trying to get the bytes of %s using %s",
                                requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString,
                            HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

