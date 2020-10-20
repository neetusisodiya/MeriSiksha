package com.muravtech.merishiksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.Response.CalendarEventListBean;
import com.muravtech.merishiksha.adapter.EventListAdapter;
import com.muravtech.merishiksha.admin_ui.AddCalendarEventActivity;
import com.muravtech.merishiksha.module.MyEventDay;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;
import com.muravtech.merishiksha.utils.CustomTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by neetu on 26.05.2020.
 */

public class CalendarActivity extends AppCompatActivity {

    @BindView(R.id.imageview_add)
    ImageView imageviewAdd;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.rv_eventDate)
    RecyclerView rvEventDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    EventListAdapter eventListAdapter;
    ArrayList<CalendarEventListBean.DataBean> mList;
    ArrayList<CalendarEventListBean.DataBean> eventList;
    List<EventDay> events;
    String type;
    private AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        events = new ArrayList<>();
        eventList = new ArrayList<>();

        if (getIntent() != null && !getIntent().getStringExtra("type").equalsIgnoreCase("")) {
            type=getIntent().getStringExtra("type");
            if (type.equalsIgnoreCase("admin"))
                imageviewAdd.setVisibility(View.VISIBLE);
        }
        rvEventDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Calendar calendar = Calendar.getInstance();
        //events.add(new MyEventDay(calendar, R.drawable.ic_favorite, "I am Event"));
        //events.add(new EventDay(calendar, DrawableUtils.getThreeDots(CalendarActivity.this)));
        setEvent(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));


        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -1);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 6);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);


        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                if (eventDay != null) {
                    setEvent(new SimpleDateFormat("yyyy-MM-dd").format(eventDay.getCalendar().getTime()));

                }
            }
        });
        //getCalendarList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(eventList!=null)
            eventList.clear();
         getCalendarList();
    }

    private void setEvent(String date) {
        eventList.clear();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getDate().equalsIgnoreCase(date)) {
                eventList.add(mList.get(i));
            }

        }
        eventListAdapter = new EventListAdapter(CalendarActivity.this, eventList);
        rvEventDate.setAdapter(eventListAdapter);

    }

    private void getCalendarList() {
        CommonUtils.progressDialogShow(this);
        Call<CalendarEventListBean> call = APIClient.getClient().create(DKRInterface.class)
                .getCalendarEventList(AppPreferences.getSchoolId());
        call.enqueue(new Callback<CalendarEventListBean>() {
            @Override
            public void onResponse(Call<CalendarEventListBean> call, Response<CalendarEventListBean> response) {
                if (response.body() != null) {
                    // Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    CalendarEventListBean childListBean1 = response.body();
                    CommonUtils.hideProgressDoalog();
                    mList.addAll(childListBean1.getData());
                    for (int i = 0; i < mList.size(); i++) {
                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(dateFormat.parse(mList.get(i).getDate()));
                            if (mList.get(i).getHoliday().equalsIgnoreCase("0"))
                                events.add(new MyEventDay(calendar, R.drawable.active_dot, mList.get(i).getTitle()));
                            else
                                events.add(new MyEventDay(calendar, R.drawable.circle_blue, mList.get(i).getTitle()));
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
            public void onFailure(Call<CalendarEventListBean> call, Throwable t) {
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
