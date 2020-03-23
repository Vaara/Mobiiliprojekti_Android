package com.example.propertymaintenance;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask {

    private static final String TAGI = "Download Task";
    private Context context;

    private String downloadURL = "", downloadFileName = "";
    private ProgressDialog progressDialog;

    public DownloadTask(Context context, String downloadURL){

        this.context = context;

        this.downloadURL = downloadURL;

        downloadFileName = downloadURL.substring(downloadURL.lastIndexOf('/'), downloadURL.length());

        Log.e(TAGI, downloadFileName);

        new DownloadingTask().execute();
    }

    public class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Download in progress");
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    progressDialog.dismiss();
                    ContextThemeWrapper ctw = new ContextThemeWrapper(context, R.style.Theme_AppCompat_Dialog_Alert);
                    final AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(ctw);
                    alertDialogueBuilder.setTitle("Document ");
                    alertDialogueBuilder.setMessage("Document downloaded successfully!");
                    alertDialogueBuilder.setCancelable(false);
                    alertDialogueBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialogueBuilder.setNegativeButton("Open flaw report", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/CodePlayon/" + downloadFileName);
                            Uri path = Uri.fromFile(pdfFile);
                            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                            pdfIntent.setDataAndType(path, "application/pdf");
                            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            try {
                                context.startActivity(pdfIntent);
                            } catch (ActivityNotFoundException e){
                                Toast.makeText(context, "No application available for PDF viewing!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alertDialogueBuilder.show();
                    Toast.makeText(context, "Document downloaded A-OK!", Toast.LENGTH_SHORT).show();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e(TAGI, "Download unsuccessful");
                }
            } catch (Exception e) {
                e.printStackTrace();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);

                Log.e(TAGI, "Download unsuccessful with exception - " + e.getLocalizedMessage());
            }

            super.onPostExecute(result);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadURL);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAGI, "Server returned HTTP " + c.getResponseCode() + " " + c.getResponseMessage());
                }

                if (new CheckForSDCard().isSDCardPresent()){
                    apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + "CodePlayon");
                } else
                    Toast.makeText(context, "No SD Card in place!", Toast.LENGTH_SHORT).show();

                if(!apkStorage.exists()){
                    apkStorage.mkdir();
                    Log.e(TAGI, "Directory created!");
                }

                outputFile = new File(apkStorage, downloadFileName);

                if(!outputFile.exists()){
                    outputFile.createNewFile();
                    Log.e(TAGI, "Eile created!");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;

                while((len1 = is.read(buffer)) != -1){
                    fos.write(buffer, 0, len1);
                }

                fos.close();
                is.close();
            } catch (Exception e){
                e.printStackTrace();
                outputFile = null;
                Log.e(TAGI, "Download error exceotion " + e.getMessage());
            }

            return null;
        }
    }
}
