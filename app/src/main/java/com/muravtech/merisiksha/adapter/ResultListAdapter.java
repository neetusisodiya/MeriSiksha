package com.muravtech.merisiksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.interfaces.OnItemClickListener;
import com.muravtech.merisiksha.module.InstuctionBean;
import com.muravtech.merisiksha.utils.CommonUtils;
import com.muravtech.merisiksha.utils.CustomTextView;

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.VideoViewHolder> {
    Context context;
    List<InstuctionBean.DataBean> listSubjectName;
    OnItemClickListener onItemClickListener;
    String type;

    public ResultListAdapter(Context context, List<InstuctionBean.DataBean> listSubjectName, OnItemClickListener onItemClickListener,String type) {
        this.context = context;
        this.listSubjectName = listSubjectName;
        this.onItemClickListener = onItemClickListener;
        this.type = type;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.result_model, parent, false));
    }
    @Override

    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
//        holder.ExamListGroupName.setText(listSubjectName.get(position).getExam_name());
        holder.ExamListExamName.setText(" : "+listSubjectName.get(position).getExam_name());
        holder.ExamListTotalQuestion.setText(" : "+listSubjectName.get(position).getTotal_questions());
        holder.tv_totalMarks.setText(" : "+listSubjectName.get(position).getTotal_marks());
        holder.ExamListQuestionTime.setText(" : "+ listSubjectName.get(position).getDuration());

        if(listSubjectName.get(position).getStatus().equalsIgnoreCase("1"))
            holder.result_publish.setVisibility(View.GONE);
        holder.result_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(position);

            }
        });
        if(type.equalsIgnoreCase("student")){
            holder.result_publish.setVisibility(View.GONE);
        }
    }

    public int getItemCount() {
        return listSubjectName.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        CustomTextView ExamListExamName, ExamListTotalQuestion, tv_totalMarks, ExamListQuestionTime;
        CardView message;
        Button result_publish;

        public VideoViewHolder(View itemView) {
            super(itemView);
            message = (CardView) itemView.findViewById(R.id.message);
            ExamListExamName = (CustomTextView) itemView.findViewById(R.id.ExamListExamName);
            ExamListTotalQuestion = (CustomTextView) itemView.findViewById(R.id.ExamListTotalQuestion);
            tv_totalMarks = (CustomTextView) itemView.findViewById(R.id.tv_totalMarks);
            ExamListQuestionTime = (CustomTextView) itemView.findViewById(R.id.ExamListQuestionTime);
            result_publish = (Button) itemView.findViewById(R.id.result_publish);

        }
    }
}
