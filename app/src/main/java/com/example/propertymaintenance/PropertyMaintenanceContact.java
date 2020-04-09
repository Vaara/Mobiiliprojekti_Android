package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import org.w3c.dom.Text;


public class PropertyMaintenanceContact extends BaseActivity implements View.OnClickListener{


    TextView textView;
    TextView propMainName;
    TextView propPhoneNumber;
    TextView propEmail;
    TextView propAddress;
    Button feedbackButton;
    String name, phoneNumber, eMail, homeAddress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        jSONParse();

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_property_maintenance_contact;
    }

    @Override
    protected void doStuff() {
        setContentView(R.layout.activity_property_maintenance_contact);
        feedbackButton = findViewById(R.id.feedbackButton);
        feedbackButton.setOnClickListener((View.OnClickListener) this);
        jSONParse();

    }

    private void jSONParse() {

        RequestQueue queue = Volley.newRequestQueue(this);

        propMainName = findViewById(R.id.propertyCompanyNameField);
        propPhoneNumber = findViewById(R.id.numberField);
        propEmail = findViewById(R.id.emailField);
        propAddress = findViewById(R.id.addressField);

        String url = "http://ec2-18-234-159-189.compute-1.amazonaws.com/propertymaintenance/1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject result = jsonArray.getJSONObject(i);

                                String company = result.getString("Name");
                                String phone = result.getString("Phone");
                                String email = result.getString("Email");
                                String address = result.getString("Address");

                                propMainName.setText(company);
                                propPhoneNumber.setText(phone);
                                propEmail.setText(email);
                                propAddress.setText(address);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "ErrOR" + e.getLocalizedMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.feedbackButton) {
            Intent intent = new Intent(PropertyMaintenanceContact.this, PropertyMaintenanceFeedback.class);
            startActivity(intent);
        }
    }
}
