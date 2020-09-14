package com.muravtech.merisiksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.SubjectListBean;
import com.muravtech.merisiksha.interfaces.OnItemClickListener;
import com.muravtech.merisiksha.module.InstuctionBean;
import com.muravtech.merisiksha.utils.CommonUtils;
import com.muravtech.merisiksha.utils.CustomTextView;

import java.util.ArrayList;
import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.VideoViewHolder> {
    Context context;
    List<InstuctionBean.DataBean> listSubjectName;
    OnItemClickListener onItemClickListener;

    public ExamListAdapter(Context context, List<InstuctionBean.DataBean> listSubjectName, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listSubjectName = listSubjectName;
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.exam_test_priview, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
//        holder.ExamListGroupName.setText(listSubjectName.get(position).getExam_name());
        holder.ExamListExamName.setText(" : "+listSubjectName.get(position).getExam_name());
        holder.ExamListTotalQuestion.setText(" : "+listSubjectName.get(position).getTotal_questions());
        holder.tv_totalMarks.setText(" : "+listSubjectName.get(position).getTotal_marks());
        holder.ExamListQuestionTime.setText(" : "+ CommonUtils.ConvertInMinute(listSubjectName.get(position).getDuration()));
        holder.message.setOnClickListener(new View.OnClickListener() {
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
        CustomTextView ExamListExamName, ExamListTotalQuestion, tv_totalMarks, ExamListQuestionTime, tv_total_subject,ExamListGroupName;
        CardView message;
        Button reult;

        public VideoViewHolder(View itemView) {
            super(itemView);
            message = (CardView) itemView.findViewById(R.id.message);
            ExamListExamName = (CustomTextView) itemView.findViewById(R.id.ExamListExamName);
            ExamListTotalQuestion = (CustomTextView) itemView.findViewById(R.id.ExamListTotalQuestion);
            tv_totalMarks = (CustomTextView) itemView.findViewById(R.id.tv_totalMarks);
            tv_total_subject = (CustomTextView) itemView.findViewById(R.id.tv_total_subject);
            ExamListQuestionTime = (CustomTextView) itemView.findViewById(R.id.ExamListQuestionTime);
            reult = (Button) itemView.findViewById(R.id.examStart);

        }
    }
}
