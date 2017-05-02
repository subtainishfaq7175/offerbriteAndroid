package com.vb.offerbrite.data;

import android.content.Context;

public class SharedPrefs {

    private Context context;


    private final String PREF_KEY_NAME = "offer_brite";
    private final String PREF_KEY_USER_NAME = "user_name";
    private final String PREF_KEY_EMAIL = "user_email";
    private final String PREF_KEY_STATUS = "user_status";
    private final String PREF_KEY_USER_IMAGE = "user_image";
    private final String PREF_KEY_USER_TYPE = "user_type";
    private final String PREF_KEY_USER_ID = "user_id";
    private final String PREF_KEY_TOKEN = "user_token";
    private final String PREF_KEY_PROFILE_PERCENTAGE = "user_profile_percentage";

    public SharedPrefs(Context context) {
        this.context = context;
    }

    public void setToken(String token) {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_KEY_TOKEN, "");
    }

    public void setUserName(String userName) {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEY_USER_NAME, userName);
        editor.apply();
    }

    public String getUserName() {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_KEY_USER_NAME, "");
    }

    public void setUserEmail(String email) {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEY_EMAIL, email);
        editor.apply();
    }

    public String getUserEmail() {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_KEY_EMAIL, "");
    }

    public void setStatus(int status) {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_KEY_STATUS, status);
        editor.apply();
    }

    public void setImage(String image) {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEY_USER_IMAGE, image);
        editor.apply();
    }

    public String getUserImage() {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_KEY_USER_IMAGE, "");
    }

    public void setUserType(int userType) {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_KEY_USER_TYPE, userType);
        editor.apply();
    }

    public void setUserId(int id) {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_KEY_USER_ID, id);
        editor.apply();
    }

    public void setProfilePercentage(int profilePercentage) {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_KEY_PROFILE_PERCENTAGE, profilePercentage);
        editor.apply();
    }

    public int getProfilePercentage() {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(PREF_KEY_PROFILE_PERCENTAGE, 0);
    }

    public int getUserId() {
        android.content.SharedPreferences preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(PREF_KEY_USER_ID, 0);
    }
}
