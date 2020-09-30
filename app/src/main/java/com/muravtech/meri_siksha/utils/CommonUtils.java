package com.muravtech.meri_siksha.utils;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.muravtech.meri_siksha.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtils {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
//    public static void dismissDialog(){
//        if (customProgressDialogTwo!= null) {
//            customProgressDialogTwo.dismiss();
//        }
//    }
//    public static void showProDialog1(Context context){
//        customProgressDialogTwo=new CustomProgressDialog(context, R.drawable.logo);
//        customProgressDialogTwo.setCancelable(false);
//
//        customProgressDialogTwo.show();
//    }
//    private static CustomProgressDialog customProgressDialogTwo;

    public static final int REQUEST_CAMERA_ACCESS_PERMISSION = 5674;
    public static final int TAKE_PICTURE = 6352;
    public static final int PICK_Gallery_IMAGE = 12345;
    public static int PERMISSION_ALL = 999;
    private static Dialog progressdialog;
    public static String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void progressDialogShow(Context context){

        progressdialog = new Dialog(context);
        progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressdialog.setContentView(R.layout.view_progress);
//        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        // Set the progress dialog background color transparent
        progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // pDialog = new ProgressDialog(context);
        // pDialog.setMessage("Loading..");
        progressdialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        progressdialog.setMessage("Loading...");
       // progressdialog.setCancelable(false);
        progressdialog.show();

    }
    public static void hideProgressDoalog(){
        if (progressdialog.isShowing()) {
            try {
                progressdialog.dismiss();
            } catch (Exception e) {

            }
        }
    }


    /**
     * Check internet availabilty
     *
     * @param mContext Context of activity or fragment
     * @return Returns true is internet connected and false if no internet connected
     */
    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isEmailValid(String email) {

        Pattern pattern;
        Matcher matcher;

        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * Show toast message
     *
     * @param mContext Context of activity or fragment
     * @param message  Message that show into the Toast
     */
    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Show alert dialog
     *
     * @param mContext         Context of activity o fragment
     * @param message          Message that shows on Dialog
     * @param title            Title for dialog
     * @param positiveText     Set positive text
     * @param positiveListener Set functionality on positive button click
     * @param negativeListener Set functionality on negative button click
     * @param negativeText     Negative button text
     * @param neutralText      Neturat button text
     * @param neutralListener  Set Netural button listener
     * @param isCancelable     true -> Cancelable True ,false -> Cancelable False
     * @return dialog
     */
    public static AlertDialog.Builder showDialog(Context mContext, String message, String title, String positiveText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener, String negativeText, String neutralText, DialogInterface.OnClickListener neutralListener, boolean isCancelable) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setNegativeButton(negativeText, negativeListener);
        alert.setPositiveButton(positiveText, positiveListener);
        alert.setNeutralButton(neutralText, neutralListener);
        alert.setCancelable(isCancelable);
        try {
            alert.show();

        } catch (Exception e) {

        }
        return alert;
    }

    public static AlertDialog.Builder showMessageDialog(Context mContext, String title, String message) {
        return showDialog(mContext, message, title, mContext.getString(R.string.ok), null, null, null, null, null, true);
    }

    /**
     * Check weather device is GPS is Enabled or not.
     *
     * @param mContext Context of the Activity or fragment.
     * @return Returns true if GPS Enabled and false when its not.
     */
    public static boolean isGpsEnabled(Context mContext) {
        return ((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Check weather device support PlayServices or not.
     *
     * @param mContext Context of the Activity od Fragment.
     * @return Returns true if device support PlayServices and false when its not.
     */
    /*public static boolean checkPlayServices(Context mContext) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(mContext);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog((Activity) mContext, result, 9000).show();
            }
            return false;
        }else{
            return true;
        }
    }*/

    /**
     * Simple Share Intent
     *
     * @param mContext Context of the Activity or Fragment.
     * @param text     Text that you want to share with intent
     */
    public static void shareContent(Context mContext, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(sendIntent, "Go To : "));
    }

    /**
     * Simple Browser Intent
     *
     * @param mContext Context of the Activity or Fragment.
     * @param url      Url that you want to open in Browser
     */
    public static void intentToBrowser(Context mContext, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(Intent.createChooser(intent, "Go To : "));
        } catch (Exception e) {

        }
    }

    /**
     * Intent to Mail App
     *
     * @param mContext  Context of the Activity or Fragment.
     * @param addresses Email Id that you want to intent into Mail App
     * @param subject   Subject fromChat email
     * @param message   Message for email
     */
    public static void intentToMail(Context mContext, String[] addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }

    /**
     * Intent to Phone
     *
     * @param mContext Context of the Activity or Fragment.
     * @param number   Number on which want to make a call
     */
    public static void intentToPhone(Context mContext, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
        mContext.startActivity(Intent.createChooser(intent, "Go To : "));
    }

    /**
     * Intent to Google Map
     *
     * @param mContext Context of the Activity or Fragment.
     * @param mLat     Latitude
     * @param mLong    Longitude
     */
    public static void intentToMap(Context mContext, String mLat, String mLong) {
        String label = mContext.getString(R.string.app_name);
        String uriBegin = "geo:" + mLat + "," + mLong;
        String query = mLat + "," + mLong + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=10";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(Intent.createChooser(intent, "Go To : "));
    }

    /**
     * Intent to Google Map
     *
     * @param mContext       Context of the Activity or Fragment.
     * @param activityToOpen Class to open
     */
    public static void intentToActivity(Context mContext, Class activityToOpen) {
        Intent intent = new Intent(mContext, activityToOpen);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * Go to playstore link of app
     *
     * @param mContext Context of the Activity or Fragment.
     */
    public static void sharePlayStoreLink(Context mContext) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getPackageName()));
            mContext.startActivity(Intent.createChooser(intent, "Go To : "));
        } catch (Exception e) {

        }
    }

    /**
     * Get playstore link of app
     *
     * @param mContext Context of the Activity or Fragment.
     */
    public static String getPlayStoreLink(Context mContext) {
        return "https://play.google.com/store/apps/details?id=" + mContext.getPackageName();
    }

    /**
     * Check weather device is Tablet or not.
     *
     * @param mContext Context of the Activity.
     * @return Returns true if device is Tablet and false when its not.
     */
    public static boolean isTablet(Context mContext) {
        return (mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Get current date
     *
     * @return Returns current date
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentdate = formatter.format(c.getTime());
        return currentdate;
    }

    /**
     * Get current time
     *
     * @return Returns current Time
     */
    public static String getCurrentTime() {
        //kk:mm:ss 24 Hour Format
        //hh:mm:ss a 12 Hour Format
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
        String currentTime = formatter.format(c.getTime());
        return currentTime;
    }

    /**
     * Show Log
     *
     * @param message Message that want to show into Log
     */
    public static void showLog(String message) {
        Log.e("LOG", "" + message);
    }
    ///  Utils.loadImage(mContext, userPOJO.getImageURL(), mImage, R.drawable.ic_user_placeholder, R.drawable.ic_user_placeholder);

    /**
     * Goto any Fragment
     *
     * @param mContext  Context of the Activity of Fragment.
     * @param fragment  Fragment that want to open
     * @param container Container in which want to infulate Fragment
     */
    public static void goToFragment(Context mContext, Fragment fragment, int container, boolean addtoBackstack) {
        FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment);
        if (addtoBackstack)
            transaction.addToBackStack(null);
        try {
            transaction.commit();
        } catch (Exception e) {

        }
    }

    /**
     * Goto any Fragment
     *
     * @param mContext  Context of the Activity of Fragment.
     * @param fragment  Fragment that want to open
     * @param container Container in which want to infulate Fragment
     */
/*
    public static void goToFragmentWithAnimation(Context mContext, Fragment fragment, int container, boolean addtoBackstack) {
        android.support.v4.app.FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.exit_anim, R.anim.enter_anim);
        transaction.replace(container, fragment);
        if (addtoBackstack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
*/

    /**
     * Change status Bar color
     *
     * @param mContext Context of the Activity.
     * @param act      Activity,in which want to change status bar color
     * @param color    Color that you want
     */
    public static void changeStatusBarColor(Context mContext, Activity act, int color) {
        if (Build.VERSION.SDK_INT > 20) {
            Window window = act.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(mContext, color));
        }
    }

    /**
     * Hide Soft Keyboard
     *
     * @param mContext Context of the Activity or Fragment.
     * @param view     Current focus of View
     */
    public static void hideKeyboard(Context mContext, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Set Typeface into TextView
     *
     * @param mContext Context of the Activity or Fragment.
     * @param tv       TextView to set Typeface
     */
    public static void typefaceFont(Context mContext, TextView tv) {
        Typeface faceb = Typeface.createFromAsset(mContext.getAssets(), "bauhaus.ttf");
        tv.setTypeface(faceb);
    }

    /**
     * Convert Temperature fromChat Kelvin to Celsius
     *
     * @param temp Temperature that want to convert
     * @return Returns Temperature in Celsius
     */
    public static String convertK2C(String temp) {
        float result = 0;
        result = Float.parseFloat(temp) - Float.parseFloat("273.15");
        return String.format("%.1f", result);
    }

    /**
     * Convert Temprature fromChat Kelvin to Fahrenheit
     *
     * @param temp Temperature that want to convert
     * @return Returns Temperature in Fahrenheit
     */
    public static String convertK2F(String temp) {
        float result = 0;
        result = (Float.parseFloat(temp) - Float.parseFloat("273.15")) * Float.parseFloat("1.8000") + Float.parseFloat("32.00");
        return String.format("%.1f", result);
    }

    /**
     * Get day fromChat Timestamp
     *
     * @param TimeInMilis TimeStamp
     * @return Returns day according to give Timestamp
     */
    public static String getDayFromTimeStamp(String TimeInMilis) {
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(TimeInMilis) * 1000);
        weekDay = dayFormat.format(calendar.getTime());
        return weekDay;
    }

    /**
     * Get Date fromChat Timestamp
     *
     * @param TimeInMilis Timestamp
     * @return Returns Date according to give Timestamp
     */
    public static String getDateFromTimeStamp(String TimeInMilis) {
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(TimeInMilis) * 1000);
        weekDay = dayFormat.format(calendar.getTime());
        return weekDay;
    }

    /**
     * Compare Time with Current Time
     *
     * @param datetime Date-Time that you want to compare with current datetime
     *                 (Format : yyyy-mm-dd hh:mm:ss, Example : 2016-06-07 21:00:00 Time will be in 24 Hour Format)
     * @return True if Time is smaller than current time otherwise return false
     */
    public static boolean compareTimewithCurrentTime(String datetime) {
        Calendar c = Calendar.getInstance();
        Date currentTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date timeCompare = sdf.parse(datetime);
            currentTime = c.getTime();
            return timeCompare.compareTo(currentTime) >= 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check is Current time is in between Start and End time or not
     *
     * @param starttime Start Date-Time
     * @param endtime   End Date-Time
     *                  (Format : yyyy-mm-dd hh:mm:ss, Example : 2016-06-07 21:00:00 Time will be in 24 Hour Format)
     * @return True if Current Time is in between start and end time than current time otherwise return false
     */
    public static boolean compareTimeBetweenTwoTimes(String starttime, String endtime) {
        Calendar c = Calendar.getInstance();
        Date currentTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            currentTime = c.getTime();
            Date startCompare = sdf.parse(starttime);
            Date endCompare = sdf.parse(endtime);
            if (startCompare.compareTo(currentTime) <= 0 && endCompare.compareTo(currentTime) >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * To get unique number
     *
     * @return Unique Integer
     */
    public static int getUniqueNumber() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static void loadImage(final Context mContext, final File file, final ImageView imageView, final int errorPlaceholder, final int placeholder) {

        ///  Glide.with(mContext).load(file).into(imageView);
        Picasso.with(mContext)
                .load(file)
                .into(imageView);
    }


    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("Twitter!", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }
    public static String ConvertInMinute(String second){
        int minute=Integer.parseInt(second)/60;
        return String.valueOf(minute);
    }
    /**
     * To Bitmap of any drawable image
     *
     * @param mContext      Context of Activity or Fragment
     * @param drawableImage Drawable image
     * @return Return Bitmap of any image
     */
    public static Bitmap getBitmapFromDrawable(Context mContext, int drawableImage) {
        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), drawableImage);
        return icon;
    }



    /**
     * Compile all html tags and shows an output which is same as in browser.
     *
     * @param text Text to convert in html.
     * @return String which is the output without the html tags.
     */
    public static String fromHtmlToString(String text) {
        String result;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            result = Html.fromHtml(text).toString();
        }
        return result;
    }

    /**
     * Make an activity full screen . This will show soft keys at the bottom if that device have soft keys.
     *
     * @param mContext Context of activity to make full screen.
     */
    public static void fullScreen(Context mContext) {
        ((AppCompatActivity) mContext).getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        ;
    }



    public static DatePickerDialog showDatePicker(Context context, final TextView dateView) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateView.setText(dateFormat.format(selectedDate.getTime()));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        return datePickerDialog;
    }

    public static String convertDateToDisplayDate(Date dateToConvert) {
        String time = "";
        try {
            Calendar messageDate = Calendar.getInstance();
            messageDate.setTime(dateToConvert);
            Calendar todayDate = Calendar.getInstance();

            if (messageDate.get(Calendar.DAY_OF_YEAR) == todayDate.get(Calendar.DAY_OF_YEAR) && messageDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR)) {
                time = "today " + new SimpleDateFormat("HH:mm").format(messageDate.getTime());
            } else {
                time = new SimpleDateFormat("dd MMM yy HH:mm").format(messageDate.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


}
