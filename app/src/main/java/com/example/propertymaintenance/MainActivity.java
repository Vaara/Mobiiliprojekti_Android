package com.example.propertymaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
    }

    public void logout() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progress_dialog_logout_fi));
        progressDialog.show();
        //Toast.makeText(MainActivity.this, R.string.toast_logout_fi,Toast.LENGTH_LONG).show();
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogout) {
            logout();
        }
    }
}
