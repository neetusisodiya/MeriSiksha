package com.muravtech.merishiksha.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.Response.ChatModelList;
import com.muravtech.merishiksha.utils.AppPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by neetu on 21/6/18.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    //private UserDetails userDetails;
    String user_name, user_id, user_image;
    private Activity activity;
    private List<ChatModelList.DataBean> data = new ArrayList<>();

    public ChatAdapter(Activity activity) {
        this.activity = activity;
       // user_name = AppPreferences.getAccessId(activity, Constants.USERNAME);
        user_id = AppPreferences.getAccessId();
       // user_image = AppPreferences.getAccessId(activity, Constants.USER_IMG);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(activity).inflate(R.layout.left_chat_item, null);

        } else {
            view = LayoutInflater.from(activity).inflate(R.layout.right_chat_item, null);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text_message.setText(data.get(position).getMessage());

        if (data.get(position).getChat_type().equalsIgnoreCase("Group")) {
            if (user_id.equalsIgnoreCase(String.valueOf(data.get(position).getUser_id()))) {
                holder.tv_name.setVisibility(View.GONE);
            } else {
                holder.tv_name.setVisibility(View.VISIBLE);
                holder.tv_name.setText(data.get(position).getUsername());
            }
        } else {
            holder.tv_name.setVisibility(View.GONE);
        }

        try {
//            2018-07-10 06:47:55
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df2.setTimeZone(TimeZone.getTimeZone("UTC"));
//
            Date chatDate = df2.parse(data.get(position).getCreated_at());
//            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//            calendar.setTime(chatDate);

            SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Date date1 = new Date();
            Date nowDate = outputFmt.parse(outputFmt.format(date1));

            long diff = nowDate.getTime() - chatDate.getTime();
            long sec = diff / 1000;
            long min = sec / 60;
            long hour = min / 60;
            long days = hour / 24;

            if (days != 0) {
                Log.e("days", "" + days);
                holder.txt_message_date.setText(days + activity.getString(R.string.days_ago));
            } else if (hour != 0) {
                Log.e("hour", "" + hour);
                holder.txt_message_date.setText(hour + activity.getString(R.string.hours_ago));
            } else if (min != 0) {
                Log.e("min", "" + min);
                holder.txt_message_date.setText(min +activity.getString(R.string.min_ago));
            } else {
                Log.e("sec", "" + sec);
                if (sec < 1) {
                    holder.txt_message_date.setText(R.string.just_now);

                } else {
                    holder.txt_message_date.setText(sec + activity.getString(R.string.sec_ago));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (user_id.equalsIgnoreCase(String.valueOf(data.get(position).getUser_id()))) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ChatModelList.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_message_date)
        TextView txt_message_date;
        @BindView(R.id.text_message)
        TextView text_message;
        @BindView(R.id.tv_name)
        TextView tv_name;

//        @BindView(R.id.iv_profile_pic)
//        CircleImageView iv_profile_pic;
//
//        @BindView(R.id.tv_user_name)
//        TextView tv_user_name;
//        @BindView(R.id.tv_other_user_name)
//        TextView tv_other_user_name;

        @BindView(R.id.l_left)
        LinearLayout l_left;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
