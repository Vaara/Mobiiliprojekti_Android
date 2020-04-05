package com.example.propertymaintenance;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
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

import java.util.ArrayList;

public class HousingCooperativeListActivity extends BaseActivity {

    private ListView listViewHousingCooperativeList;
    private RequestQueue requestQueue;
    private HousingCooperativeListAdapter housingCooperativeListAdapter;
    private HousingCooperativeObject housingCooperativeObject;
    private ProgressDialog progressDialog;
    private ArrayList<HousingCooperativeObject> housingList;
    private PopupWindow popupWindow;
    private int PROPERTY_MAINTENANCE_ID;

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
        PROPERTY_MAINTENANCE_ID = SessionManagement.getUserPropertyMaintenanceIDFromSharedPrefs();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.board_progress_dialog_fi));
        progressDialog.show();

        setToolbarTitle(getString(R.string.app_subtitle_housing_cooperative_list));
        housingCooperativeRequest();

        listViewHousingCooperativeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

                int[] textViewIDs = new int[] {
                        R.id.textViewHousingPopupName, R.id.textViewHousingPopupAddress, R.id.textViewHousingPopupApartments,
                        R.id.textViewHousingPropertyManagement, R.id.textViewHousingPopupWasteManagement
                };
                String[] textTitles = new String[] {
                        getString(R.string.housing_list_popup_name_fi),
                        getString(R.string.housing_list_popup_address_fi),
                        getString(R.string.housing_list_popup_apartments_fi),
                        getString(R.string.housing_list_popup_property_management_fi),
                        getString(R.string.housing_list_popup_waste_management_fi)
                };
                String[] textFields = new String[] {
                        housingList.get(i).getName(),
                        housingList.get(i).getAddress(),
                        housingList.get(i).getApartments(),
                        housingList.get(i).getPropertyManagement(),
                        housingList.get(i).getWasteManagement()
                };

                createPopupWindow(textViewIDs, textTitles, textFields);
                popupWindow.dismiss();
                popupWindow.showAsDropDown(view, 0, -175);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        return super.dispatchTouchEvent(ev);
    }

    private void createPopupWindow(int[] textViewIDs, String[] textTitles, String[] textFields) {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_housing_cooperative, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setClippingEnabled(false);

        for (int i = 0; i < textViewIDs.length; i++) {
            TextView textViewName = popupView.findViewById(textViewIDs[i]);
            textViewName.setText(Html.fromHtml("<b>" + textTitles[i] + "</b>" + textFields[i]));
        }

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
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject results = jsonArray.getJSONObject(i);
                                    int housingID = results.getInt("idHousingCooperative");
                                    int propertyID = results.getInt("idPropertyMaintenance");
                                    String name = results.getString("Name");
                                    String address = results.getString("Address");
                                    String apartments = results.getString("Apartments");
                                    String propertyManagement = results.getString("PropertyManagement");
                                    String wasteManagement = results.getString("WasteManagement");
                                    if (propertyID == PROPERTY_MAINTENANCE_ID) {
                                        createHousingObject(housingID, propertyID, name, address, apartments, propertyManagement, wasteManagement);
                                    }

                                }
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            createHousingObject(0, 0, getString(R.string.error_server_fi), getString(R.string.error_ask_retry_fi),
                                    "", "", "");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                createHousingObject(0, 0, getString(R.string.error_server_fi), getString(R.string.error_ask_retry_fi),
                        "", "", "");
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private void createHousingObject(int housingID, int propertyID, String name, String address, String apartments, String propertyManagement, String wasteManagement) {
        housingCooperativeObject = new HousingCooperativeObject(housingID, propertyID, name, address, apartments, propertyManagement, wasteManagement);
        housingList.add(housingCooperativeObject);
        listViewHousingCooperativeList.setAdapter(housingCooperativeListAdapter);
    }


}
