package com.muravtech.merisiksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.StudntListBean;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.VideoViewHolder> {
    Context context;
    ArrayList<StudntListBean.DataBean> listSubjectName;


    public StudentListAdapter(Context context, ArrayList<StudntListBean.DataBean> listSubjectName) {
        this.context = context;
        this.listSubjectName = listSubjectName;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_groupchild, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        holder.subject_name.setText("Name : "+listSubjectName.get(position).getName());
        holder.tv_class.setText("Class : "+listSubjectName.get(position).getClass_name());
        if(listSubjectName.get(position).getRoll_no()!=null && !listSubjectName.get(position).getRoll_no().equalsIgnoreCase("null")) {
            holder.tv_roll_no.setText("Roll no. : " + listSubjectName.get(position).getRoll_no());
        }else {
            holder.tv_roll_no.setVisibility(View.GONE);
        }
        if(listSubjectName.get(position).getAttendence_id()!=null && !listSubjectName.get(position).getAttendence_id().equalsIgnoreCase("null")) {
            holder.checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable.right_blue));
        }else {
            holder.checkBox.setButtonDrawable(R.drawable.right_brdr);
        }

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, VideoSubjectTopicsClass.class);
//                intent.putExtra("id",listSubjectName.get(position).getId());
//                intent.putExtra("className",VideoSubjectClass);
//               context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return listSubjectName.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name,tv_roll_no,tv_class;
        CheckBox checkBox;
        RelativeLayout ll_main;

        public VideoViewHolder(View itemView) {
            super(itemView);
            subject_name = (TextView) itemView.findViewById(R.id.tv_username);
            tv_roll_no = (TextView) itemView.findViewById(R.id.tv_roll_no);
            tv_class = (TextView) itemView.findViewById(R.id.tv_class);
            checkBox = (CheckBox) itemView.findViewById(R.id.iv_check);
            ll_main = (RelativeLayout) itemView.findViewById(R.id.ll_main);

        }
    }
}
