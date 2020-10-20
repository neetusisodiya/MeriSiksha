//package com.muravtech.merisiksha.onlineexam.utilities;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.text.TextUtils;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
//import com.online.demo.models.UpdateFragmentModel;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
//public class Physics extends Application {
//
//    public static List<UpdateFragmentModel> updateFragmentModelList;
//    private final static long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;
//    public static final String TAG = Application.class.getSimpleName();
//
//
//
//    private RequestQueue mRequestQueue;
//    private static Physics mInstance;
//    public static Context MY_APP_CONTEXT = null;
//    public static String MY_APP_SHARED_PREFERENCES = "online_xm";
//    public static SharedPreferences mPreferences;
//
//
//
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
//        }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        setMyappContext(getApplicationContext());
//        updateFragmentModelList = new ArrayList<UpdateFragmentModel>();
//        mInstance = this;
//
//    }
//
//
//
//    public static Context getMyappContext() {
//        return MY_APP_CONTEXT;
//    }
//
//    public void setMyappContext(Context mContext) {
//        MY_APP_CONTEXT = mContext;
//    }
//
//
//    public static SharedPreferences getSharedPreferences(Context context) {
//        return context.getSharedPreferences(MY_APP_SHARED_PREFERENCES, 0);
//
//    }
//
//    public static void writeIntPreference(String key, int value) {
//        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
//        SharedPreferences.Editor mEditor = mPreferences.edit();
//        mEditor.putInt(key, value);
//        mEditor.commit();
//
//    }
//
//    public static void writeStringPreference(String key, String value) {
//        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
//        SharedPreferences.Editor mEditor = mPreferences.edit();
//        mEditor.putString(key, value);
//        mEditor.commit();
//
//    }
//
//    public static String ReadStringPreferences(String key) {
//
//        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
//        return mPreferences.getString(key, "");
//
//    }
//
//    public static int ReadIntPreferences(String key) {
//
//        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
//        return mPreferences.getInt(key, 0);
//
//    }
//
//    public static void ClearPriferences(String key) {
//
//        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
//        SharedPreferences.Editor mEditor = mPreferences.edit();
//        mEditor.remove(key);
//        mEditor.commit();
//
//    }
//
//
//   public static synchronized Physics getInstance() {
//        return mInstance;
//    }
//
//    public RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//
//        return mRequestQueue;
//    }
//
//
//    public void cancelPendingRequests(Object tag) {
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(tag);
//        }
//    }
//
//    public static String getTime(){
//
//        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        Date date = new Date();
//        return dateFormat.format(date);
//
//    }
//
//    private static long getDateToLong(Date date) {
//        return Date.UTC(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
//    }
//
//
//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//        getRequestQueue().add(req);
//        req.setRetryPolicy(new DefaultRetryPolicy(
//                0,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }
//
//    public <T> void addToRequestQueue(Request<T> req) {
//        req.setTag(TAG);
//        getRequestQueue().add(req);
//        req.setRetryPolicy(new DefaultRetryPolicy(
//                0,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }
//}
