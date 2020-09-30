package com.muravtech.meri_siksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.ClassListBean;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class ClassWiseListAdapter extends RecyclerView.Adapter<ClassWiseListAdapter.VideoViewHolder> {
    Context context;
    ArrayList<ClassListBean.DataBean> listSubjectName;
    int i=0;
    OnItemClickListener onItemClickListener;

    public ClassWiseListAdapter(Context context, ArrayList<ClassListBean.DataBean> listSubjectName, OnItemClickListener onItemClickListener) {
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
//        if(isEven(i)){
//            holder.ll_main.setBackgroundColor(context.getResources().getColor(R.color.news));
//        }else {
//            holder.ll_main.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
//        }
        holder.subject_name.setText("Class : "+listSubjectName.get(position).getClassX());
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
