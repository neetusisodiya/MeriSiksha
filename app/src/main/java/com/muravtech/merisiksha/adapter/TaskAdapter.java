package com.muravtech.merisiksha.adapter;

/**
 * Created by neetu on 27-06-2020.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.TaskListBean;
import com.muravtech.merisiksha.interfaces.OnItemClickListener;
import com.muravtech.merisiksha.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    Context mContext;
    String taskId, staffId,type;
    private List<TaskListBean.DataBean> mData;
    OnItemClickListener onItemClickListener;

    public TaskAdapter(Context mContext, ArrayList<TaskListBean.DataBean> mData, String type, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.type = type;
        this.onItemClickListener = onItemClickListener;
    }

//    public void setData1(List<TaskListBean.DataBean> mListData) {
//        this.mData = mListData;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int taskStatus;

        final TaskListBean.DataBean item = mData.get(position);

        holder.linear.setVisibility(View.GONE);

        if (item.getTitle() != null)
            holder.textView_title.setText(item.getTitle());
        else {
            holder.textView_title.setText("no data");
        }


        if (item.getCreated_date() != null)
            holder.textView_taskcreate.setText(Utils.DateFormate(item.getCreated_date()));
        else {
            holder.textView_taskcreate.setText("no data");
        }

        if (item.getComplete_data() != null)
            holder.textView_taskcompletion.setText(Utils.DateFormate(item.getComplete_data()));
        else {
            holder.textView_taskcompletion.setText("no data");
        }

        if (item.getDescription() != null)
            holder.textView_desc.setText(item.getDescription());
        else {
            holder.textView_desc.setText("no data");
        }
        if(type.equalsIgnoreCase("admin")){
            holder.button_done.setVisibility(View.GONE);
        }
        if (item.getStatus() != null) {
            taskStatus = Integer.parseInt(item.getStatus());
            if (taskStatus == 1) {

                holder.button_done.setBackgroundResource(R.drawable.circle_blue);

            }
            if (taskStatus == 0) {

                holder.button_done.setBackgroundResource(R.drawable.redshape);
                holder.button_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.button_done.setBackgroundResource(R.drawable.circle_blue);
                        onItemClickListener.onClick(position);
                    }
                });

            }

        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_title, textView_taskcreate, textView_taskcompletion, textView_desc, textView_teachers, textView_teacher_name;
        Button button_done;
        LinearLayout linear;

        public ViewHolder(@NonNull View row) {
            super(row);
            textView_title = (TextView) row.findViewById(R.id.textView_title);
            textView_taskcreate = (TextView) row.findViewById(R.id.textView_taskcreate);
            textView_taskcompletion = (TextView) row.findViewById(R.id.textView_taskcompletion);
            textView_desc = (TextView) row.findViewById(R.id.textView_desc);
            textView_teachers = (TextView) row.findViewById(R.id.textView_teachers);
            textView_teacher_name = (TextView) row.findViewById(R.id.textView_teacher_name);
            button_done = (Button) row.findViewById(R.id.button_done);
            linear = (LinearLayout) row.findViewById(R.id.linear);
        }
    }
}
