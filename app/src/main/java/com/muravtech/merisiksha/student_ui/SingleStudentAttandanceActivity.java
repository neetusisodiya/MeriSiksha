package com.muravtech.merisiksha.student_ui;

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
import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.SingleStudentAttadenceBean;
import com.muravtech.merisiksha.adapter.EventListAdapter;
import com.muravtech.merisiksha.admin_ui.AddCalendarEventActivity;
import com.muravtech.merisiksha.server.APIClient;
import com.muravtech.merisiksha.server.DKRInterface;
import com.muravtech.merisiksha.utils.AppPreferences;
import com.muravtech.merisiksha.utils.CommonUtils;
import com.muravtech.merisiksha.utils.CustomTextView;
import com.muravtech.merisiksha.utils.DrawableUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        events = new ArrayList<>();

        preferences = AppPreferences.getPrefs(this);
        rvEventDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -1);

        Calendar twoDaysAgo = (Calendar) min.clone();
        twoDaysAgo.add(Calendar.MONTH, -5);
        Log.e("TAG", "date>>>>>>: "+twoDaysAgo.getTimeInMillis());

        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 6);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        titleTxt.setText("Student Attendance");

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

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
                .getAttendence(AppPreferences.getSchoolId(), AppPreferences.getAccessId(), "2020-06-06", "2020-06-07");
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
