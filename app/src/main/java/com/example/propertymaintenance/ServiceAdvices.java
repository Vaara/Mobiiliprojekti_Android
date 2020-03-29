package com.example.propertymaintenance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class ServiceAdvices extends BaseActivity implements View.OnClickListener {


    private int USER_ID;
    private int USER_LEVEL;
    private int USER_HOUSING_COOPERATIVE_ID;
    private int IMAGE_CAPTURE_CODE = 1001;
    private static final int PICK_IMAGE = 1002;

    private static String userAddress;
    CheckBox checkBoxContactResident;
    CheckBox checkBoxMasterKey;
    Button sendButton;
    private RequestQueue requestQueue;
    private int masterKey;
    private int contactResident;
    EditText edTitleProblem;
    EditText edProblemMessage;
    EditText edAdditionalMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_service_advices;
    }

    @Override
    protected void doStuff() {

        edTitleProblem = findViewById(R.id.titleProblem);
        edProblemMessage = findViewById(R.id.problemMessage);
        edAdditionalMessage = findViewById(R.id.additionalInfoMessage);
        findViewById(R.id.imageButtonCamera).setOnClickListener(this);
        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        checkBoxMasterKey = findViewById(R.id.masterkeyAllowed);
        checkBoxContactResident = findViewById(R.id.callAndMakeAppointment);
        checkBoxMasterKey.setOnClickListener(this);
        checkBoxContactResident.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        USER_ID = new SessionManagement(this).getUserIdFromSharedPrefs();
        USER_LEVEL = new SessionManagement(this).getUserLevelFromSharedPrefs();
        USER_HOUSING_COOPERATIVE_ID = new SessionManagement(this).getUserHousingCooperativeIdFromSharedPrefs();

        getUserAddress();

    }

    public void pickImage() {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButtonCamera) {

            final CharSequence[] items = {"Ota kuva", "Valitse Galleriasta", "Peruuta"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lisää kuva");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Ota kuva")) {

                        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                        startActivity(intent);

                    } else if (items[item].equals("Valitse Galleriasta")) {

                        pickImage();

                    } else if (items[item].equals("Peruuta")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }


        if (v.getId() == R.id.sendButton && edProblemMessage != null) {

            if (checkBoxMasterKey.isChecked()) {
                masterKey = 1;
                Log.d("vikailmoitus", "masterkey");
            }
            if (checkBoxContactResident.isChecked()) {
                contactResident = 1;
                Log.d("vikailmoitus", "soita ja sovi");
            }
            sendMessage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            //  mImageView.setImageURI(imageUri);

        }
    }
    private void sendMessage(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-234-159-189.compute-1.amazonaws.com/serviceadvice/";
        Log.d ("problemMessage",edProblemMessage.getText().toString());
        Log.d ("problemMessage",edTitleProblem.getText().toString());
        Log.d ("problemMessage",edAdditionalMessage.getText().toString());


        JSONObject serviceAdvice = new JSONObject();
        try {
            serviceAdvice.put("idResidents", USER_ID);
            serviceAdvice.put("idHousingCooperative", USER_HOUSING_COOPERATIVE_ID);
            serviceAdvice.put("ServiceMessageTitle",edTitleProblem.getText().toString());
            serviceAdvice.put("ServiceMessage", edProblemMessage.getText().toString());
            serviceAdvice.put("AdditionalMessage", edAdditionalMessage.getText().toString());
            serviceAdvice.put("MasterKeyAllowed", masterKey);
            serviceAdvice.put("ContactResident", contactResident);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, serviceAdvice,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                          //  Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                        //Log.d("viesti2", String.valueOf(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(ServiceAdvices.this, error.getMessage(),Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Response:  " + error.toString(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        Log.d("viesti", "Error in lähetys");
                    }
                }
        );
        queue.add(getRequest);

    }

    private void getUserAddress() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlBasepart = "http://ec2-18-234-159-189.compute-1.amazonaws.com/resident/";
        String urlIdPart = SessionManagement.getUserIdFromSharedPrefs().toString();
        String url = urlBasepart + urlIdPart;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject results = jsonArray.getJSONObject(i);
                                userAddress = results.getString("Address");
                                Log.d("userAddress", userAddress);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ServiceAdvices.this, error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        );
        queue.add(request);
    }
}
