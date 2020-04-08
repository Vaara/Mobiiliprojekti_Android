package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static Integer userIdResponse;
    private static Integer userLevelResponse;
    private static String userFullNameResponse;
    private static Integer userHousingCooperativeIdResponse;
    private static Integer userPropertyMaintenanceIdResponse;

    private EditText edUsername;
    private EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.etUsername);
        edPassword = findViewById(R.id.etPassword);
        findViewById(R.id.btnLogin).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        Integer userId = sessionManagement.getSession();

        if (userId != -1) {
            moveToMainActivity();
        }
        else {
            // Do nothing
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            BaseActivity.hideSoftKeyboard(this);
            checkLoginCredentials();
        }
    }

    public void checkLoginCredentials() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progress_dialog_login_fi));
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://ec2-18-234-159-189.compute-1.amazonaws.com/login";

        JSONObject loginCredentials = new JSONObject();
        try {
            loginCredentials.put("username", edUsername.getText().toString());
            loginCredentials.put("password", edPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, loginCredentials,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        try {
                            userIdResponse = response.getInt("userId");
                            userLevelResponse = response.optInt("userLevel");
                            userFullNameResponse = response.optString("userFullName");
                            userHousingCooperativeIdResponse = response.optInt("userHousingCooperativeId");
                            userPropertyMaintenanceIdResponse = response.optInt("userPropertyMaintenanceId");
                            login();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, R.string.toast_login_failed_fi, Toast.LENGTH_LONG).show();
                            Log.d("Login", "catch in LoginRequestResponse");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, R.string.toast_login_failed_fi, Toast.LENGTH_LONG).show();
                        Log.d("Login", "Error in LoginRequest");
                    }
                }
        );
        queue.add(getRequest);
    }

    public static Integer getUserIdResponse() {
        return userIdResponse;
    }

    public static Integer getUserLevelResponse() {
        return userLevelResponse;
    }

    public static String getUserFullNameResponse() {
        return userFullNameResponse;
    }

    public static Integer getUserHousingCooperativeIdResponse() {
        return userHousingCooperativeIdResponse;
    }

    public static Integer getUserPropertyMaintenanceIdResponse() {
        return userPropertyMaintenanceIdResponse;
    }

    public void login() {
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        sessionManagement.saveSession(userIdResponse);
        moveToMainActivity();
    }

    public void moveToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
