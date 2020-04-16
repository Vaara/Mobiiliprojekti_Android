package com.example.propertymaintenance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.text.method.ScrollingMovementMethod;
import android.widget.CheckBox;
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
    private TextView serviceHeader, serviceMessage, serviceAdditional, serviceResidentName, serviceResidentPhone, serviceResidentAddress;
    private CheckBox checkBoxContactRequest, checkBoxMasterKeyAllowed;

    private static int serviceID;
    private static int residentId;

    ProgressDialog progressDialog;

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
        setToolbarTitle("Vikailmoitukset");

        serviceHeader = findViewById(R.id.serviceHeader);
        serviceMessage = findViewById(R.id.serviceMessage);
        serviceAdditional = findViewById(R.id.serviceAdditionalInfo);
        serviceResidentName = findViewById(R.id.serviceUserName);
        serviceResidentPhone = findViewById(R.id.serviceUserPhone);
        serviceResidentAddress = findViewById(R.id.serviceUserAddress);

        serviceMessage.setMovementMethod(new ScrollingMovementMethod());
        serviceAdditional.setMovementMethod(new ScrollingMovementMethod());

        checkBoxContactRequest = findViewById(R.id.callMeMaybe);
        checkBoxMasterKeyAllowed = findViewById(R.id.masterKeyPermission);

        RequestQueue queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progress_dialog_loading_fi));

        String urlServiceAdvice = getString(R.string.api_base) + getString(R.string.api_service_advice) + SessionManagement.getUserPropertyMaintenanceIDFromSharedPrefs();

        Intent getID = getIntent();
        serviceID = getID.getIntExtra("serviceID", -999);

        queue.add(serviceAdvice(urlServiceAdvice));

        //sendReport();
    }

    private JsonObjectRequest serviceAdvice(String url) {
        progressDialog.show();
        JsonObjectRequest getServiceAdvice = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String messageTitle;
                        String message;
                        String additionalMessage;
                        Integer masterKey;
                        Integer contactRequest;

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject ServiceAdvice = jsonArray.getJSONObject(i);

                                int checkID = ServiceAdvice.getInt("idServiceAdvices");
                                if (checkID == serviceID) {
                                    residentId = ServiceAdvice.getInt("idResidents");
                                    residentDetails(residentId);
                                    messageTitle = ServiceAdvice.getString("ServiceMessageTitle");
                                    message = ServiceAdvice.getString("ServiceMessage");
                                    additionalMessage = ServiceAdvice.getString("AdditionalMessage");

                                    masterKey = ServiceAdvice.getInt("MasterKeyAllowed");
                                    contactRequest = ServiceAdvice.getInt("ContactResident");

                                    /*
                                    String imageId = ServiceAdvice.getString("ImageId");
                                    String isDone = ServiceAdvice.getString("Done");
                                    */

                                    if (additionalMessage == null || additionalMessage == "null") {
                                        additionalMessage = "";
                                    }

                                    if(masterKey == 1)
                                    {
                                        checkBoxMasterKeyAllowed.setChecked(true);
                                    }

                                    if(contactRequest == 1)
                                    {
                                        checkBoxContactRequest.setChecked(true);
                                    }

                                    serviceHeader.setText(messageTitle);
                                    serviceMessage.setText(message);
                                    serviceAdditional.setText(additionalMessage);

                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //textView.setText(error.toString());
                    }
                });
        //progressDialog.dismiss();
        return getServiceAdvice;
    }

    private void residentDetails(Integer id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlResidentDetails = getString(R.string.api_base) + getString(R.string.api_resident);
        String url = urlResidentDetails + id;

        JsonObjectRequest getResidentInfo = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String name;
                        String phone;
                        String address;
                        try {

                            JSONArray residentArray = response.getJSONArray("results");
                            JSONObject residentInfo = residentArray.getJSONObject(0);
                            name = residentInfo.getString("Name");
                            phone = residentInfo.getString("Phone");
                            address = residentInfo.getString("Address");

                            serviceResidentName.setText(name);
                            serviceResidentPhone.setText(phone);
                            serviceResidentAddress.setText(address);

                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });
        queue.add(getResidentInfo);
    }

    public void sendReport() {
        final ProgressDialog progressDialogReport = new ProgressDialog(this);
        progressDialogReport.setMessage(getString(R.string.progress_dialog_sending_fi));
        progressDialogReport.show();

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
                    progressDialogReport.dismiss();

                    if (response.equalsIgnoreCase("201")) {
                        Toast.makeText(CustodianServiceAdviceViewDetails.this,
                                R.string.toast_report_sent_fi, Toast.LENGTH_LONG).show();
                    }
                    else if (response.equalsIgnoreCase("500")){
                        Toast.makeText(CustodianServiceAdviceViewDetails.this,
                                R.string.error_server_fi, Toast.LENGTH_LONG).show();
                    }
                    Log.d("ServiceAdviceReport", "onResponse()" + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialogReport.dismiss();
                    Toast.makeText(CustodianServiceAdviceViewDetails.this,
                            getString(R.string.error_server_fi), Toast.LENGTH_LONG).show();
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
