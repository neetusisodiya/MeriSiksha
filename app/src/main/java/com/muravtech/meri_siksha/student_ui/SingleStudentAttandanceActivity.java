package com.muravtech.meri_siksha.student_ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.SingleStudentAttadenceBean;
import com.muravtech.meri_siksha.adapter.EventListAdapter;
import com.muravtech.meri_siksha.admin_ui.AddCalendarEventActivity;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;
import com.muravtech.meri_siksha.utils.CustomTextView;
import com.muravtech.meri_siksha.utils.DrawableUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by neetu on 26.05.2020.
 */

public class SingleStudentAttandanceActivity extends AppCompatActivity {

    @BindView(R.id.imageview_add)
    ImageView imageviewAdd;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.rv_eventDate)
    RecyclerView rvEventDate;
    @BindView(R.id.title_txt)
    TextView titleTxt;

    private AppPreferences preferences;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    EventListAdapter eventListAdapter;

    ArrayList<SingleStudentAttadenceBean.DataBean> mList;
    //  ArrayList<CalendarEventListBean.DataBean> eventList;
    List<EventDay> events;
    String student_id,name;
    int currentYear,currentMonth;
    String startDate,endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        ButterKnife.bind(this);
        student_id=getIntent().getStringExtra("student_id");
        name=getIntent().getStringExtra("name");
        mList = new ArrayList<>();
        events = new ArrayList<>();

        preferences = AppPreferences.getPrefs(this);
        rvEventDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -9);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 1);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        titleTxt.setText(name);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;

        startDate=String.valueOf(currentYear)+"-"+String.valueOf(currentMonth)+"-01";
        endDate=String.valueOf(currentYear)+"-"+String.valueOf(currentMonth)+"-30";

        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                if(currentMonth>1){
                    currentMonth= currentMonth-1;
                }else {
                    currentYear= currentYear-1;
                }
                startDate=String.valueOf(currentYear)+"-"+String.valueOf(currentMonth)+"-01";
                endDate=String.valueOf(currentYear)+"-"+String.valueOf(currentMonth)+"-30";
                getAttandenceList();
            }
        });
        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                if(currentMonth<12){
                    currentMonth= currentMonth+1;
                }else {
                    currentYear= currentYear+1;
                }
                endDate=String.valueOf(currentYear)+"-"+String.valueOf(currentMonth)+"-30";
                startDate=String.valueOf(currentYear)+"-"+String.valueOf(currentMonth)+"-01";
                getAttandenceList();
            }
        });


        getAttandenceList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // getCalendarList();
    }

    private void getAttandenceList() {
        CommonUtils.progressDialogShow(this);
        Call<SingleStudentAttadenceBean> call = APIClient.getClient().create(DKRInterface.class)
                .getAttendence(AppPreferences.getSchoolId(),student_id, startDate, endDate);
        call.enqueue(new Callback<SingleStudentAttadenceBean>() {
            @Override
            public void onResponse(Call<SingleStudentAttadenceBean> call, Response<SingleStudentAttadenceBean> response) {
                if (response.body() != null) {
                    // Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    SingleStudentAttadenceBean childListBean1 = response.body();
                    CommonUtils.hideProgressDoalog();
                    mList.addAll(childListBean1.getData());

                    for (int i = 0; i < mList.size(); i++) {
                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(dateFormat.parse(mList.get(i).getAttendance()));

                            if (mList.get(i).getStatus().equalsIgnoreCase("0"))
                                events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(SingleStudentAttandanceActivity.this, "A")));
                            else
                                events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText1(SingleStudentAttandanceActivity.this, "P")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                    calendarView.setEvents(events);
                } else {
                    CommonUtils.hideProgressDoalog();
                    ((CustomTextView) findViewById(R.id.empty_view)).setVisibility(View.VISIBLE);
                    ((CustomTextView) findViewById(R.id.empty_view)).setText("No Data Found!!!");
                    //.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SingleStudentAttadenceBean> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.imageview_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.imageview_add:
                startActivity(new Intent(this, AddCalendarEventActivity.class));
                break;
        }
    }
}



