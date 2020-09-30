package com.muravtech.meri_siksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;
import com.muravtech.meri_siksha.module.Notification;
import com.muravtech.meri_siksha.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotficationAdapter extends RecyclerView.Adapter<NotficationAdapter.VideoViewHolder> {
    Context context;
    List<Notification.DataBean> listSubjectName;
    OnItemClickListener onItemClickListener;

    public NotficationAdapter(Context context, List<Notification.DataBean> listSubjectName, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listSubjectName = listSubjectName;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        //holder.subject_name.setWidth(Wra);
        holder.tvMessage.setText(listSubjectName.get(position).getMessage());
        holder.tvTime.setText(Utils.timeDateFormate(listSubjectName.get(position).getCreated_at()));
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(position);

            }
        });
    }

    public int getItemCount() {
        return listSubjectName.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_main)
        LinearLayout llMain;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
