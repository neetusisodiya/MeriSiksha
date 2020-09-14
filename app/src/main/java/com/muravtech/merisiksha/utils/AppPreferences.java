package com.muravtech.merisiksha.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.muravtech.merisiksha.Response.ResponseLoginGetIngo;

public class AppPreferences {
    private static final String SHARED_PREFERENCE_NAME = "MERISIKASHA";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String PASSWORD = "PASSWORD";
    public static final String MEMBER_ID = "MEMBER_ID";
    public static final String IMAGE = "Image";
    public static final String FatherName = "FATHER_NAME";
    public static final String REMEMBER_ME = "REMEMBER_ME";

    ////login data save//
    public static final String ID = "ID";
    public static final String SCHOOLID = "SCHOOLID";
    public static final String CLASS_ID = "CLASSID";

    public static final String NAME = "NAME";
    public static final String SECTION_ID = "SECTION_ID";
    public static final String CONTACT = "CONTACT";
    public static final String EMAIL = "EMAIL_ID";
    public static final String DOB = "DOB";
    public static final String institute_name = "institute_name";
    public static final String LOGO = "LOGO";
    public static SharedPreferences mPrefs;
    private Context context;
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String Loginbean = "Loginbean";
    public static final String Type = "type";

    public AppPreferences(Context context) {
        this.context = context;
        mPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreferences getPrefs(Context context) {
        return new AppPreferences(context);

    }

    public String getStringValue(String Key) {
        return mPrefs.getString(Key, "");
    }

    public boolean getBooleanValue(String Key) {
        return mPrefs.getBoolean(Key, false);
    }



    public void setStringValue(String Key, String value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(Key, value);
        editor.commit();
    }


    public ResponseLoginGetIngo getLoginBean(String Key) {
        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        ResponseLoginGetIngo obj = gson.fromJson(json, ResponseLoginGetIngo.class);
        return obj;

    }
    public void setLoginBean(String Key, ResponseLoginGetIngo responseLoginGetIngo) {
        Gson gson = new Gson();
        String json = gson.toJson(responseLoginGetIngo);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(Key, json);
        editor.commit();

    }

    public void clearPrefsdata() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear().commit();

    }

    public void setBooleanValue(String Key, boolean value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(Key, value);
        editor.commit();
    }

    public String getAccessToken() {
        return mPrefs.getString(AppPreferences.ACCESS_TOKEN, "");
    }


    public void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.commit();

    }

    public static String getAccessId() {

        return mPrefs.getString(AppPreferences.ID, "");
    }


    public static void setAccessId(String id) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(ID, id);
        editor.commit();

    }

    public static String getSchoolId() {

        return mPrefs.getString(AppPreferences.SCHOOLID, "");
    }


    public static void setSchoolId(String id) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(SCHOOLID, id);
        editor.commit();

    }

    public static String getSectionId() {

        return mPrefs.getString(AppPreferences.SECTION_ID, "");
    }


    public static void setSectionId(String id) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(SECTION_ID, id);
        editor.commit();

    }
}
