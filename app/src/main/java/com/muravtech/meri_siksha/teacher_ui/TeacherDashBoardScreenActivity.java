package com.muravtech.meri_siksha.teacher_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.muravtech.meri_siksha.Application;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.chat.ChatTabLayoutActivity;
import com.muravtech.meri_siksha.chat.ConnectionService;
import com.muravtech.meri_siksha.common.CalendarActivity;

import com.muravtech.meri_siksha.common.ClassWiseListActivity;
import com.muravtech.meri_siksha.common.EditProfile;
import com.muravtech.meri_siksha.common.LoginNewActivity;
import com.muravtech.meri_siksha.common.GalleryActivity;
import com.muravtech.meri_siksha.common.NotificationListActivity;
import com.muravtech.meri_siksha.student_ui.BaseActivity;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherDashBoardScreenActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.iv_bell)
//    ImageView ivBell;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private AppPreferences mAppPreferences;
    TextView textViewName,textViewEmailId;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_teacherr_new__menu);
        ButterKnife.bind(this);
        Application.setmSocket(null);
        Application.getSocket(this);
        startService(new Intent(getApplicationContext(), ConnectionService.class));
        setToolbar();

        mAppPreferences = new AppPreferences(TeacherDashBoardScreenActivity.this);
        // getSupportActionBar().setTitle(mAppPreferences.getStringValue(AppPreferences.institute_name));
        //getTimeFromAndroid();
//textViewName.setText(UserNAMME);
//textViewEmailId.setText(UserEmail);


        View headerView = navigationView.getHeaderView(0);
        textViewName = headerView.findViewById(R.id.textheadName);
        textViewEmailId = headerView.findViewById(R.id.textHeadEmailId);
        textViewName.setText(mAppPreferences.getStringValue(AppPreferences.NAME));
        textViewEmailId.setText(mAppPreferences.getStringValue(AppPreferences.EMAIL));
        //textViewEmailId.setText(UserEmail);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }
        });

        // TextView navUsername = (TextView) headerView.findViewById(R.id.textViewName);
        // TextView navUserEmail = (TextView) headerView.findViewById(R.id.textViewEmailId);
        //    textViewName.setText(mAppPreferences.getStringValue(AppPreferences.NAME));
        //  mAppPreferences.setStringValue(AppPreferences.PASSWORD, password);

        //    textViewEmailId.setText(mAppPreferences.getStringValue(AppPreferences.EMAIL_ID));
        navigationView.setNavigationItemSelectedListener(this);
        setDrawerLayout();

//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swing_up_left);
//        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swing_up_right);
//        cvExam.startAnimation(animation);
//        cvVideoList.startAnimation(animation2);
//        cvNotesList.startAnimation(animation);
//        cvNewsList.startAnimation(animation);
//        cvEbook.startAnimation(animation2);
//        cvNoti.startAnimation(animation2);

    }


    private void setDrawerLayout() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                navigationView.setCheckedItem(navigationView.getMenu().getItem(0).getItemId());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.ic_back_arrow));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notification) {
            startActivity(new Intent(TeacherDashBoardScreenActivity.this, NotificationListActivity.class));
        }
        if (id == R.id.nav_manage) {
            startActivity(new Intent(TeacherDashBoardScreenActivity.this, ChatTabLayoutActivity.class).putExtra("type", "teacher"));
        } else if (id == R.id.nav_logout) {
            Utils.showDecisionDialog(TeacherDashBoardScreenActivity.this, "Logout", "Do you really want to logout?", new Utils.AlertCallback() {
                public void callback() {
                    LogoutFromApp();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    @OnClick({R.id.cvnotification,R.id.imageview_notification, R.id.rl_task, R.id.cvChat, R.id.stu_attadance, R.id.cvUploadDoc, R.id.cvDailyQuiz, R.id.cvDiary, R.id.cvCalender, R.id.cvNotesList, R.id.cvGallery})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_notification:
            case R.id.cvnotification:
                startActivity(new Intent(TeacherDashBoardScreenActivity.this, NotificationListActivity.class));
                break;
            case R.id.rl_task:
                Intent intent = new Intent(TeacherDashBoardScreenActivity.this, TeacherTaskActivity.class);
                intent.putExtra("type", "teacher");
                intent.putExtra("id", "teacher_id");
                startActivity(intent);
                break;
            case R.id.cvChat:
               startActivity(new Intent(TeacherDashBoardScreenActivity.this, ChatTabLayoutActivity.class)
                       .putExtra("type", "teacher"));

                break;
            case R.id.stu_attadance:
                startActivity(new Intent(TeacherDashBoardScreenActivity.this, ClassWiseListActivity.class)
                        .putExtra("type", "teacher")
                        .putExtra("from", "attadance"));
                break;
            case R.id.cvUploadDoc:
                startActivity(new Intent(TeacherDashBoardScreenActivity.this, ClassWiseListActivity.class)
                        .putExtra("type", "teacher")
                        .putExtra("from", "uploads"));
                break;
            case R.id.cvDailyQuiz:
                startActivity(new Intent(TeacherDashBoardScreenActivity.this, ExeleFileActivity.class));
                break;
            case R.id.cvDiary:
                startActivity(new Intent(TeacherDashBoardScreenActivity.this, ClassWiseListActivity.class)
                        .putExtra("type", "teacher")
                        .putExtra("from", "diary"));
                break;
            case R.id.cvCalender:
                startActivity(new Intent(TeacherDashBoardScreenActivity.this, CalendarActivity.class)
                        .putExtra("type", "teacher"));
                break;
            case R.id.cvNotesList:
                startActivity(new Intent(TeacherDashBoardScreenActivity.this, ClassWiseListActivity.class)
                        .putExtra("type", "teacher")
                        .putExtra("from", "notes"));
                break;
            case R.id.cvGallery:
                startActivity(new Intent(TeacherDashBoardScreenActivity.this, GalleryActivity.class));
                break;
        }
    }
    public void LogoutFromApp() {
        showProgressDialog();
        try {
            mAppPreferences.setBooleanValue(AppPreferences.IS_LOGIN, false);
            mAppPreferences.clearPrefsdata();
            dismissProgressDialog();
            startActivity(new Intent(TeacherDashBoardScreenActivity.this, LoginNewActivity.class));
            finishAffinity();
            finish();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
