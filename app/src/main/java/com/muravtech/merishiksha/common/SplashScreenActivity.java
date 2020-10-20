package com.muravtech.merishiksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.admin_ui.AdminDashBoardScreenActivity;
import com.muravtech.merishiksha.student_ui.BaseActivity;
import com.muravtech.merishiksha.student_ui.StudentDashBoardScreenActivity;
import com.muravtech.merishiksha.teacher_ui.TeacherDashBoardScreenActivity;
import com.muravtech.merishiksha.utils.AppPreferences;

public class SplashScreenActivity extends BaseActivity {
    private static long SPLASH_TIME_OUT = 1000;
    private Handler handler;
    private static AppPreferences mAppPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.splash_screen);
        handler = new Handler();
        mAppPreferences = new AppPreferences(SplashScreenActivity.this);

       // Crashlytics.getInstance().crash(); // Force a crash

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rippleBackground.startRippleAnimation();
//            }
//        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAppPreferences.getBooleanValue(AppPreferences.IS_LOGIN)) {
                    if(mAppPreferences.getStringValue(AppPreferences.Type).equalsIgnoreCase("9")) {
                        Intent i = new Intent(SplashScreenActivity.this, AdminDashBoardScreenActivity.class);
                        startActivity(i);
                        finish();
                    }else if(mAppPreferences.getStringValue(AppPreferences.Type).equalsIgnoreCase("3")) {
                        Intent i = new Intent(SplashScreenActivity.this, StudentDashBoardScreenActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashScreenActivity.this, TeacherDashBoardScreenActivity.class);
                        startActivity(i);
                        finish();
                    }
                    // close this activity

                } else {
                    startActivity(new Intent(SplashScreenActivity.this, LoginNewActivity.class));
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);



    }
}
