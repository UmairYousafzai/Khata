package com.example.khataapp.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferenceHelper {

    private static final String SHARED_PREFERENCE_NAME = "SharedPreference";

    private static final String BUSINESS_ID = "business id";
    private static final String userID = "User ID";
    private static final String IS_Login = "login";
    private static final String BUSINESS_NAME = "business name";
    private static final String LOCATION_ID = "location id";
    private static final String PARTY_ID = "default party id";

    private static SharedPreferenceHelper helperInstance = null;
    private static SharedPreferences sharedPreferences;

    public SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }


    public static synchronized SharedPreferenceHelper getInstance(Context context) {
        if (helperInstance == null) {
            helperInstance = new SharedPreferenceHelper(context);

        }
        return helperInstance;
    }


    public void setBUSINESS_ID(String id) {
        sharedPreferences.edit().putString(BUSINESS_ID, id).apply();
        ;

    }

    public String getBUSINESS_ID() {
        return sharedPreferences.getString(BUSINESS_ID, "0");
    }

    public void setUserID(String id) {
        sharedPreferences.edit().putString(userID, id).apply();
        ;

    }

    public String getUserID() {
        return sharedPreferences.getString(userID, "0");
    }

    public void setBusinessName(String name) {
        sharedPreferences.edit().putString(BUSINESS_NAME, name).apply();
        ;

    }

    public String getBusinessName() {
        return sharedPreferences.getString(BUSINESS_NAME, "");
    }

    public void setLocationID(String id) {
        sharedPreferences.edit().putString(LOCATION_ID, id).apply();
        ;

    }

    public String getLocationID() {
        return sharedPreferences.getString(LOCATION_ID, "");
    }

    public void setDefaultPartyID(String id) {
        sharedPreferences.edit().putString(PARTY_ID, id).apply();
        ;

    }

    public String getDefaultPartyID() {
        return sharedPreferences.getString(PARTY_ID, "");
    }

    public void setIsLogin(boolean isLogin) {
        sharedPreferences.edit().putBoolean(IS_Login, isLogin).apply();
        ;

    }

    public boolean getIS_Login() {
        return sharedPreferences.getBoolean(IS_Login, false);
    }


    public void deleteSharedPreference() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
