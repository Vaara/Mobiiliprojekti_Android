package com.example.propertymaintenance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PropertyMaintenanceFeedback extends BaseActivity implements View.OnClickListener {

    EditText edEmailTitle;
    EditText edEmailText;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_property_management;
    }

    @Override
    protected void doStuff() {

        edEmailTitle = findViewById(R.id.titleMessage);
        edEmailText = findViewById(R.id.emailText);
        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setToolbarTitle("Palautetta kiinteistöhuollolle");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sendButton) {
            if ((edEmailText != null && edEmailText.length() > 0) &&
                    (edEmailTitle != null && edEmailTitle.length() > 0)) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                sendMessage();
                progressDialog.setMessage("Lähetetään viestiä");
                progressDialog.show();
                Context context = getApplicationContext();
                String text = "Palautteesi on lähetetty kiinteistöhuollolle";
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
            if (edEmailTitle == null || edEmailTitle.length() < 1) {
                Context context = getApplicationContext();
                String text = "Viestin teksti puuttuu";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
            if (edEmailText == null || edEmailText.length() < 1) {
                Context context = getApplicationContext();
                String text = "Viestin otsikko puuttuu";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void sendMessage() {
        Intent returnBtn = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(returnBtn);
    }
}
