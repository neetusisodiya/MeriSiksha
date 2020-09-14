package com.muravtech.merisiksha.student_ui;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.muravtech.merisiksha.utils.Utils;

import java.lang.reflect.Field;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected ActionBar actionBar;
    private Dialog dialog;
    public static ProgressBar progressBar;
    protected boolean isExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        progressBar = new ProgressBar(this);
        progressBar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        dialog = new Dialog(this);
        dialog.setContentView(progressBar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar = getSupportActionBar();

        // Hack. Forcing overflow button on actionbar on devices with hardware menu button
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }
    public void adjustFontScale(Configuration configuration)
    {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
    public void showProgressDialog() {
        try {
            if (dialog != null && !dialog.isShowing() && !this.isFinishing()) {
                dialog.show();
            }
        } catch (Exception e) {

        }
    }

    public void dismissProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing() && !this.isFinishing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {

        }

    }
    public boolean isOnline(boolean showWarning) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                int numActiveNetwork = cm.getAllNetworks().length;

                for (int i = 0; i < numActiveNetwork; i++) {
                    if (cm.getNetworkCapabilities(cm.getAllNetworks()[i]).hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                        connected = true;
                        return connected;
                    }
                }
            } else {
                connected = true;
            }
        }
        if (!connected && showWarning) {
            //  Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show();
            Utils.showAlertDialog(this, "Alert!", "unable to connect please check your connection settings", false);
        }
        return connected;
    }
    @Override
    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.img_back:
                hideKeyboard();
                onBackPressed();
                break;
        }*/
    }
  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public void onBackPressed() {
       /* ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);
        if (taskList.get(0).numActivities == 1 && taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            if (isExit) {
                finish();
            } else {
                MyToast.show(this, "Press back again to exit", Toast.LENGTH_SHORT);
                isExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2 * 1000);
            }
        } else {
            super.onBackPressed();
        }*/

       super.onBackPressed();
    }

    public void hideKeyboard() {
        View v = getWindow().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
