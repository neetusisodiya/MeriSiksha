package com.muravtech.merisiksha.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.CalendarEventListBean;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.VideoViewHolder> {
    Context context;
    ArrayList<CalendarEventListBean.DataBean> listSubjectName;

    public EventListAdapter(Context context, ArrayList<CalendarEventListBean.DataBean> listSubjectName) {
        this.context = context;
        this.listSubjectName = listSubjectName;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_subject_items, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.subject_name.setGravity(Gravity.LEFT);
       // holder.subject_name.texts
        holder.subject_name.setText("Event : "+listSubjectName.get(position).getTitle());
        if(listSubjectName.get(position).getHoliday().equalsIgnoreCase("1")) {
            holder.iv_dot.setVisibility(View.VISIBLE);
        }else {
            holder.iv_dot.setVisibility(View.VISIBLE);
            //holder.iv_dot.setImageResource(context.getResources().getDrawable(R.drawable.active_dot));
            holder.iv_dot.setImageResource(R.drawable.active_dot);
        }

    }
    public int getItemCount() {
        return listSubjectName.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name;
        ImageView iv_dot;
        CardView message;
        RelativeLayout ll_main;

        public VideoViewHolder(View itemView) {
            super(itemView);
            subject_name = (TextView) itemView.findViewById(R.id.subject_name);
            message = (CardView) itemView.findViewById(R.id.message);
            ll_main = (RelativeLayout) itemView.findViewById(R.id.ll_main);
            iv_dot = itemView.findViewById(R.id.iv_dot);

        }
    }
}
