package com.example.propertymaintenance;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static String SHARED_PREFS = "sharedPrefs";
    private static String USER_ID = "userId";
    private static String USER_LEVEL = "userLevel";
    private static String USER_FULL_NAME = "userFullName";
    private static String USER_HOUSING_COOPERATIVE_ID = "userHousingCooperativeId";
    private static String USER_PROPERTY_MAINTENANCE_ID = "userPropertyMaintenanceId";
    private static String SESSION_KEY = "sessionKey";

    private Integer userIdResponse;
    private  Integer userLevelResponse;
    private String userFullNameResponse;
    private Integer userHousingCooperativeIdResponse;
    private Integer userPropertyMaintenanceIdResponse;

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Integer userId) {
        editor.putInt(SESSION_KEY, userId).commit();
        fetchUserDataFromLoginActivity();
        storeUserToSharedPrefs();
    }

    public Integer getSession() {
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void removeSession() {
        removeUserFromSharedPrefs();
        editor.putInt(SESSION_KEY, -1).commit();
    }

    public void fetchUserDataFromLoginActivity() {
        userIdResponse = LoginActivity.getUserIdResponse();
        userLevelResponse = LoginActivity.getUserLevelResponse();
        userFullNameResponse = LoginActivity.getUserFullNameResponse();
        userHousingCooperativeIdResponse = LoginActivity.getUserHousingCooperativeIdResponse();
        userPropertyMaintenanceIdResponse = LoginActivity.getUserPropertyMaintenanceIdResponse();
    }

    public void storeUserToSharedPrefs() {
        editor.putInt(USER_ID, userIdResponse);
        editor.putInt(USER_LEVEL, userLevelResponse);
        editor.putString(USER_FULL_NAME, userFullNameResponse);

        if (userLevelResponse == 0) {
            editor.putInt(USER_HOUSING_COOPERATIVE_ID, userHousingCooperativeIdResponse);
        }
        else if (userLevelResponse == 1) {
            editor.putInt(USER_PROPERTY_MAINTENANCE_ID, userPropertyMaintenanceIdResponse);
        }
        editor.commit();
    }

    public void removeUserFromSharedPrefs() {
        Integer loggedInUserId = sharedPreferences.getInt("USER_ID", -1);
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
            // Unable to remove userdata from SharedPreferences
        }
    }

    public  void clearSharedPrefs() {
        editor.clear();
        if (!editor.commit()) {
            // Unable to clear all data from SharedPreferences
        }
    }

    public static Integer getUserIdFromSharedPrefs() {
        Integer userId = sharedPreferences.getInt(USER_ID, -1);
        return userId;
    }

    public static Integer getUserLevelFromSharedPrefs() {
        Integer userLevel = sharedPreferences.getInt(USER_LEVEL, -1);
        return userLevel;
    }

    public static String getUserFullNameFromSharedPrefs() {
        String userFullName = sharedPreferences.getString(USER_FULL_NAME, "");
        return userFullName;
    }

    public static Integer getUserHousingCooperativeIdFromSharedPrefs() {
        Integer userHousingCooperativeId = sharedPreferences.getInt(USER_HOUSING_COOPERATIVE_ID, -1);
        return userHousingCooperativeId;
    }

    public static Integer getUserPropertyMaintenanceIDFromSharedPrefs() {
        Integer userPropertyMaintenanceId = sharedPreferences.getInt(USER_PROPERTY_MAINTENANCE_ID, -1);
        return userPropertyMaintenanceId;
    }
}
