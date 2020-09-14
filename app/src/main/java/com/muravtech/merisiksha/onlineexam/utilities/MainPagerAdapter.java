package com.muravtech.merisiksha.onlineexam.utilities;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;


public class MainPagerAdapter extends PagerAdapter
{

    private ArrayList<View> views = new ArrayList<View>();


    @Override
    public int getItemPosition (Object object)
    {
        int index = views.indexOf (object);
        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }


    @Override
    public Object instantiateItem (ViewGroup container, int position)
    {
        View v = views.get (position);
        container.addView (v);
        return v;
    }


    @Override
    public void destroyItem (ViewGroup container, int position, Object object)
    {
        container.removeView (views.get (position));
    }


    @Override
    public int getCount ()
    {
        return views.size();
    }


    @Override
    public boolean isViewFromObject (View view, Object object)
    {
        return view == object;
    }


    public int addView (View v)
    {
        return addView (v, views.size());
    }


    public int addView (View v, int position)
    {
        views.add (position, v);
        return position;
    }


    public int removeView (ViewPager pager, View v)
    {
        return removeView (pager, views.indexOf (v));
    }


    public int removeView (ViewPager pager, int position)
    {

        pager.setAdapter (null);
        views.remove (position);
        pager.setAdapter (this);

        return position;
    }


    public View getView (int position)
    {
        return views.get (position);
    }


}