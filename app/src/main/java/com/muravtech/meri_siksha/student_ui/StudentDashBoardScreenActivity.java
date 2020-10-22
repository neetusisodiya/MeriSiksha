package com.muravtech.meri_siksha.student_ui;

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
import com.muravtech.meri_siksha.common.ContactActivity;
import com.muravtech.meri_siksha.common.EditProfile;
import com.muravtech.meri_siksha.common.GalleryActivity;
import com.muravtech.meri_siksha.common.LoginNewActivity;
import com.muravtech.meri_siksha.common.NotificationListActivity;
import com.muravtech.meri_siksha.common.StudentDiaryListActivity;
import com.muravtech.meri_siksha.common.SubjectListActivity;
import com.muravtech.meri_siksha.onlineexam.ExamListActivity;
import com.muravtech.meri_siksha.onlineexam.ResultListForStudentActivity;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentDashBoardScreenActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gud)
    TextView gud;
 //   @BindView(R.id.NameUser)
    TextView textViewName,textViewEmailId;
//    @BindView(R.id.iv_bell)
//    ImageView ivBell;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private AppPreferences mAppPreferences;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_new__menu);
        ButterKnife.bind(this);
        Application.setmSocket(null);
        Application.getSocket(this);
        startService(new Intent(getApplicationContext(), ConnectionService.class));
        setToolbar();
        mAppPreferences = new AppPreferences(StudentDashBoardScreenActivity.this);

        View headerView = navigationView.getHeaderView(0);

        textViewName = headerView.findViewById(R.id.textheadName);
        textViewEmailId = headerView.findViewById(R.id.textHeadEmailId);

        textViewName.setText(mAppPreferences.getStringValue(AppPreferences.NAME));
        textViewEmailId.setText(mAppPreferences.getStringValue(AppPreferences.EMAIL));

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        setDrawerLayout();


    }


    private void setDrawerLayout() {

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
        if (id == R.id.nav_events) {
            startActivity(new Intent(StudentDashBoardScreenActivity.this, GalleryActivity.class));
        } else if (id == R.id.nav_contacts) {
            startActivity(new Intent(StudentDashBoardScreenActivity.this, ContactActivity.class));
        } else if (id == R.id.nav_result) {
            startActivity(new Intent(StudentDashBoardScreenActivity.this, ResultListForStudentActivity.class));
        }
        if (id == R.id.nav_cal) {
            startActivity(new Intent(StudentDashBoardScreenActivity.this, CalendarActivity.class).putExtra("type", "student"));
        } else if (id == R.id.nav_fees) {
            startActivity(new Intent(StudentDashBoardScreenActivity.this, SingleStudentFeesActivity.class)
                    .putExtra("notes", "NotesClass"));
        } else if (id == R.id.nav_logout) {
            Utils.showDecisionDialog(StudentDashBoardScreenActivity.this, "Logout", "Do you really want to logout?", new Utils.AlertCallback() {
                public void callback() {
                    LogoutFromApp();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LogoutFromApp() {
        showProgressDialog();

        try {
            mAppPreferences.setBooleanValue(AppPreferences.IS_LOGIN, false);
            mAppPreferences.clearPrefsdata();
            dismissProgressDialog();
            startActivity(new Intent(StudentDashBoardScreenActivity.this, LoginNewActivity.class));
            finishAffinity();
            finish();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @OnClick({R.id.imageview_notification, R.id.cvExam, R.id.cvVideoList, R.id.cvNotesList, R.id.cvChat, R.id.cvDailyQuiz, R.id.cvAttedence, R.id.cvDiary, R.id.cvCalender, R.id.rl_result, R.id.cvFees})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_notification:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, NotificationListActivity.class));
                break;
            case R.id.cvExam:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, ExamListActivity.class));
                break;
            case R.id.cvVideoList:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, SubjectListActivity.class)
                        .putExtra("type", "student")
                        .putExtra("id", AppPreferences.getSectionId())
                        .putExtra("from", "video"));
                break;
            case R.id.cvNotesList:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, SubjectListActivity.class)
                        .putExtra("type", "student")
                        .putExtra("id", AppPreferences.getSectionId())
                        .putExtra("from", "notes"));
                break;
            case R.id.cvChat:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, ChatTabLayoutActivity.class).putExtra("type", "student"));
                break;
            case R.id.cvDailyQuiz:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, QuizActivity.class)
                        .putExtra("news", "NewsClass"));
                break;
            case R.id.cvAttedence:
                Intent intent=new Intent(this, SingleStudentAttandanceActivity.class);
                intent.putExtra("student_id", AppPreferences.getAccessId());
                intent.putExtra("name",mAppPreferences.getStringValue(AppPreferences.NAME));
                startActivity(intent);
                break;
            case R.id.cvDiary:

                startActivity(new Intent(StudentDashBoardScreenActivity.this, StudentDiaryListActivity.class)
                        .putExtra("id", "2")
                        .putExtra("type","student"));
                break;
            case R.id.cvCalender:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, CalendarActivity.class)
                        .putExtra("type", "student"));
                break;
            case R.id.rl_result:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, ResultListForStudentActivity.class));
                break;
            case R.id.cvFees:
                startActivity(new Intent(StudentDashBoardScreenActivity.this, SingleStudentFeesActivity.class).putExtra("notes", "NotesClass"));
                break;
        }
    }
}
