package com.muravtech.merisiksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.DiaryListBean;
import com.muravtech.merisiksha.interfaces.OnItemClickListener;
import com.muravtech.merisiksha.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StudentDiaryListAdapter extends RecyclerView.Adapter<StudentDiaryListAdapter.ViewHolder> {
    private Context mContext;
    private List<DiaryListBean.DataBean> mData;
    OnItemClickListener onItemClickListener;

    public StudentDiaryListAdapter(Context mContext, ArrayList<DiaryListBean.DataBean> mData, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.student_diary, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaryListBean.DataBean item = mData.get(position);

        holder.tvTitle.setText("Title : "+item.getTitle());
        holder.tvDate.setText("Created Date : "+Utils.DateFormate(item.getDate()));

        if(item.getSubject_name()!=null && !item.getSubject_name().equalsIgnoreCase("")) {
            holder.tvSubject.setText("Subject : " + item.getSubject_name());
        }
        holder.tvClass.setText("Class : "+item.getClassX());
        holder.tvSection.setText("Section : "+item.getSection());
        holder.textViewDescription.setText("Description : "+item.getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_subject)
        TextView tvSubject;
        @BindView(R.id.tv_class)
        TextView tvClass;
        @BindView(R.id.tv_section)
        TextView tvSection;
        @BindView(R.id.textView_description)
        TextView textViewDescription;

        public ViewHolder(@NonNull View row) {
            super(row);
            ButterKnife.bind(this,row);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}