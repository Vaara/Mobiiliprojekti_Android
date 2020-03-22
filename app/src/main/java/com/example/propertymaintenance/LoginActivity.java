package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.preference.PreferenceManager.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USER_ID = "userId";
    public static final String USER_LEVEL = "userLevel";
    public static final String USER_FULL_NAME = "userFullName";
    public static final String USER_HOUSING_COOPERATIVE_ID = "userHousingCooperativeId";
    public static final String USER_PROPERTY_MAINTENANCE_ID = "userPropertyMaintenanceId";

    public static Integer userIdResponse;
    public static Integer userLevelResponse;
    public static String userFullNameResponse;
    public static Integer userHousingCooperativeIdResponse;
    public static Integer userPropertyMaintenanceIdResponse;

    EditText edUsername;
    EditText edPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.etUsername);
        edPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            checkLoginCredentials();
        }
    }

    public void checkLoginCredentials() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://ec2-18-234-159-189.compute-1.amazonaws.com/login";
        //?datatype=json

        JSONObject loginUser = new JSONObject();
        try {
            loginUser.put("username", "test1");
            loginUser.put("password", "123");
            //loginUser.put("username", edUsername.getText().toString());
            //loginUser.put("password", edPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

////////////////////////////////////////////////////////////////////////////////////////////////////

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, loginUser ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            userIdResponse = response.getInt("userId");
                            //userLevelResponse = response.optInt("userLevel");
                            //userFullNameResponse = response.optString("userFullName");
                            //userHousingCooperativeIdResponse = response.optInt("userHousingCooperativeId");
                            //userPropertyMaintenanceIdResponse = response.optInt("userPropertyMaintenanceId");

                            //storeUserToSharedPrefs();
                            //login();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(getRequest);

////////////////////////////////////////////////////////////////////////////////////////////////////




        /*
////////////////////////////////////////////////////////////////////////////////////////////////////
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

                                userIdResponse = obj.getInt("userId");
                                userLevelResponse = obj.optInt("userLevel");
                                userFullNameResponse = obj.optString("userFullName");
                                userHousingCooperativeIdResponse = obj.optInt("userHousingCooperativeId");
                                userPropertyMaintenanceIdResponse = obj.optInt("userPropertyMaintenanceId");

                                storeUserToSharedPrefs();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", "test1");
                params.put("password", "123");
                return params;
            }
        };
        queue.add(stringRequest);

////////////////////////////////////////////////////////////////////////////////////////////////////
         */

    }

    public void getUserFromSharedPrefs() {
        Integer userId = sharedPreferences.getInt(USER_ID, 99);
        Integer userLevel = sharedPreferences.getInt(USER_LEVEL, 99);
        String userFullName = sharedPreferences.getString(USER_FULL_NAME, "");
        Integer userHousingCooperativeId;
        Integer userPropertyMaintenanceId;

        if (userId == 0) {
            userHousingCooperativeId = sharedPreferences.getInt(USER_HOUSING_COOPERATIVE_ID, 99);
        }

        else {
            userPropertyMaintenanceId = sharedPreferences.getInt(USER_PROPERTY_MAINTENANCE_ID, 99);
        }
    }

    public Integer getUserIdFromSharedPrefs() {
        Integer userId = sharedPreferences.getInt(USER_ID, 99);
        return userId;
    }

    public Integer getUserLevelFromSharedPrefs() {
        Integer userLevel = sharedPreferences.getInt(USER_LEVEL, 99);
        return userLevel;
    }

    public String getUserFullNameFromSharedPrefs() {
        String userFullName = sharedPreferences.getString(USER_FULL_NAME, "");
        return userFullName;
    }

    public Integer getUserHousingCooperativeIdFromSharedPrefs() {
        Integer userHousingCooperativeId = sharedPreferences.getInt(USER_HOUSING_COOPERATIVE_ID, 99);
        return userHousingCooperativeId;

    }

    public Integer getUserPropertyMaintenanceIDFromSharedPrefs() {
        Integer userPropertyMaintenanceId = sharedPreferences.getInt(USER_PROPERTY_MAINTENANCE_ID, 99);
        return userPropertyMaintenanceId;
    }

    public void storeUserToSharedPrefs() {
        editor.putInt(USER_ID, userIdResponse);
        editor.putInt(USER_LEVEL, userLevelResponse);
        editor.putString(USER_FULL_NAME, userFullNameResponse);

        if (userIdResponse == 0) {
            editor.putInt(USER_HOUSING_COOPERATIVE_ID, userHousingCooperativeIdResponse);
        }

        else {
            editor.putInt(USER_PROPERTY_MAINTENANCE_ID, userPropertyMaintenanceIdResponse);
        }

        // If successfully stores data to SharesPreferences
        if (editor.commit()) {
            login();
        }

        else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            Log.d("Login", "Unable to store userdata to SharedPreferences");
        }
    }

    public void removeUserFromSharedPrefs() {
        Integer loggedInUserId = sharedPreferences.getInt("USER_ID", 99);

        editor.remove(USER_ID);
        editor.remove(USER_LEVEL);
        editor.remove(USER_FULL_NAME);

        if (loggedInUserId == 0) {
            editor.remove(USER_HOUSING_COOPERATIVE_ID);
        }

        else if (loggedInUserId == 1) {
            editor.remove(USER_PROPERTY_MAINTENANCE_ID);
        }

        if (!editor.commit()) {
            Log.d("Login", "Unable to remove userdata from SharedPreferences");
        }
    }

    public void login() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();  // Prevents users from coming back to this activity with backButton
    }

    public void logout() {
        removeUserFromSharedPrefs();
    }
}
