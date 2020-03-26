package com.example.propertymaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ServiceAdvices extends BaseActivity implements View.OnClickListener {


    private static final int PERMISSION_CODE = 1000;
    private int IMAGE_CAPTURE_CODE = 1001;
    private static final int PICK_IMAGE = 1002;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_service_advices;
    }

    @Override
    protected void doStuff() {
        findViewById(R.id.imageButtonCamera).setOnClickListener(this);
    }

    public void pickImage() {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,PICK_IMAGE);
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


        //imagePro.openImagePickOption();
        if (v.getId() == R.id.sendButton) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK){
          //  mImageView.setImageURI(imageUri);

             }
         }
   }
