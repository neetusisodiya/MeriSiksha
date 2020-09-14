package com.muravtech.merisiksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.ClassListBean;
import com.muravtech.merisiksha.Response.SubjectListBean;
import com.muravtech.merisiksha.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.VideoViewHolder> {
    Context context;
    ArrayList<SubjectListBean.DataBean> listSubjectName;
    int i=0;
    OnItemClickListener onItemClickListener;

    public SubjectListAdapter(Context context, ArrayList<SubjectListBean.DataBean> listSubjectName, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listSubjectName = listSubjectName;
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_subject_items, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        i=position;
        holder.subject_name.setText(listSubjectName.get(position).getSubject_name());
        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(position);

            }
        });
    }
    boolean isEven(int num) { return ((num % 2) == 0); }
    public int getItemCount() {
        return listSubjectName.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name;
        CardView message;
        RelativeLayout ll_main;

        public VideoViewHolder(View itemView) {
            super(itemView);
            subject_name = (TextView) itemView.findViewById(R.id.subject_name);
            message = (CardView) itemView.findViewById(R.id.message);
            ll_main = (RelativeLayout) itemView.findViewById(R.id.ll_main);

        }
    }
}
