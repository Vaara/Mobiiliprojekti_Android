package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class HousingContact extends BaseActivity {

    TextView textView;
    TextView housingName;
    TextView housingAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_housing_contact);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_housing_contact;
    }

    @Override
    protected void doStuff() {
        jSonParse();
    }

    public void jSonParse(){
        housingName = findViewById(R.id.housingCOPName);
        housingAddress = findViewById(R.id.addressField);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-234-159-189.compute-1.amazonaws.com/housingcooperative/1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            JSONArray array = response.getJSONArray("results");

                            for (int i  = 0; i < array.length(); i++){

                                JSONObject result = array.getJSONObject(i);

                                String name = result.getString("Name");
                                String address = result.getString("Address");

                                housingName.setText(name);
                                housingAddress.setText(address);
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
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
}
