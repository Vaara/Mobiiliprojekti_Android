package com.example.propertymaintenance;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HousingCooperativeListActivity extends BaseActivity {

    private ListView listViewHousingCooperativeList;
    private RequestQueue requestQueue;
    private HousingCooperativeListAdapter housingCooperativeListAdapter;
    private HousingCooperativeObject housingCooperativeObject;
    private ProgressDialog progressDialog;
    private ArrayList<HousingCooperativeObject> housingList;
    private PopupWindow popupWindow;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_housing_cooperative_list;
    }

    @Override
    protected void doStuff() {
        listViewHousingCooperativeList = findViewById(R.id.listViewHousingList);
        requestQueue = Volley.newRequestQueue(this);
        housingList = new ArrayList<>();
        housingCooperativeListAdapter = new HousingCooperativeListAdapter(this, housingList);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.board_progress_dialog_fi));
        progressDialog.show();

        layout = findViewById(R.id.housingCooperativeList);

        createPopupWindow();

        setToolbarTitle(getString(R.string.app_subtitle_housing_cooperative_list));
        housingCooperativeRequest();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        listViewHousingCooperativeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupWindow.dismiss();
                popupWindow.showAsDropDown(view, 0, -175);
                String name = housingList.get(i).getName();

                Log.d("position:", String.valueOf(i));
                Log.d("apartments:", housingList.get(i).getApartments());
            }
        });
    }

    private void createPopupWindow() {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_housing_cooperative, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageButton buttonClosePopup = popupView.findViewById(R.id.buttonClosePopup);
        buttonClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void housingCooperativeRequest() {
        String url = getString(R.string.api_base) + getString(R.string.api_housing_cooperative);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            if (jsonArray.length() < 1) {
                                Log.d("Response:", "EMPTY");
                            }
                            else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject results = jsonArray.getJSONObject(i);
                                    int housingID = results.getInt("idHousingCooperative");
                                    int propertyID = results.getInt("idPropertyMaintenance");
                                    String name = results.getString("Name");
                                    String address = results.getString("Address");
                                    String apartments = results.getString("Apartments");
                                    String propertyManagement = results.getString("PropertyManagement");
                                    String wasteManagement = results.getString("WasteManagement");
                                    housingCooperativeObject = new HousingCooperativeObject(housingID, propertyID, name, address, apartments, propertyManagement, wasteManagement);
                                    housingList.add(housingCooperativeObject);
                                }
                            }
                            Log.d("name:", housingList.get(0).getName());
                            listViewHousingCooperativeList.setAdapter(housingCooperativeListAdapter);
                            //listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            //titles.add(getString(R.string.error_server));
                            //messages.add(getString(R.string.error_ask_retry));
                            //listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //titles.add(getString(R.string.error_server));
                //messages.add(getString(R.string.error_ask_retry));
                //listViewBulletinBoard.setAdapter(bulletinBoardAdapter);
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

}
