package com.muravtech.merishiksha.onlineexam;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.onlineexam.models.FragmentAnalysisModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by recon on 12/30/2016.
 */

public class FragmentAnalysisAdapter extends BaseAdapter {

    public static Context activity;
    private LayoutInflater inflater;
    private List<FragmentAnalysisModel> UpdateFragmentModel;
    private static String today;



    public FragmentAnalysisAdapter(Activity updateffagmentDetailsActivity,
                                   List<FragmentAnalysisModel> UpdateFragmentModel1) {



        this.activity = updateffagmentDetailsActivity;
        this.UpdateFragmentModel = UpdateFragmentModel1;

        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public int getCount() {
        return UpdateFragmentModel.size();
    }

    @Override
    public Object getItem(int location) {
        return UpdateFragmentModel.get(location);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder  = new ViewHolder();

        final  FragmentAnalysisModel model = UpdateFragmentModel.get(position);
        if (convertView == null){
            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_result_analysis, null);

            holder.tvSubjectName = (TextView) convertView.findViewById(R.id.tvSubjectName);
            holder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
            holder.tvAttempt = (TextView) convertView.findViewById(R.id.tvAttempt);
            holder.tvCorrect = (TextView) convertView.findViewById(R.id.tvCorrect);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);

            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();

        }


        holder.tvSubjectName.setText(model.getSubject());
        holder.tvTotal.setText(model.getTotalQuestion());
        holder.tvAttempt.setText(model.getAttemped());
        holder.tvCorrect.setText(String.valueOf(model.getCorrect()));
        holder.tvTime.setText(model.getTakenTime());


        return convertView;
    }


    static class ViewHolder {


        TextView tvSubjectName,tvTotal,tvAttempt,tvCorrect,tvTime;


    }




}
