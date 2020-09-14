package com.muravtech.merisiksha.module;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class MyEventDay extends EventDay {
    private String mNote;
    private Calendar day;

    public MyEventDay(Calendar mday, int imageResource, String note) {
        super(mday,imageResource);
        mNote = note;
        day=mday;
    }
    public  String getNote() {
        return mNote;
    }
    public Calendar getDay() {
        return day;
    }

}
