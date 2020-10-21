package com.muravtech.merishiksha.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merishiksha.Bean.UpdateBean;
import com.muravtech.merishiksha.R;

import com.muravtech.merishiksha.student_ui.ExoPlayerActivity;
import com.muravtech.merishiksha.student_ui.VideoPlayer;
import com.muravtech.merishiksha.utils.Utils;

import java.util.ArrayList;

public class VideoSubjectTopicsVideoAdapter extends RecyclerView.Adapter<VideoSubjectTopicsVideoAdapter.VideoViewHolder> {
    Context context;
    ArrayList<UpdateBean.DataBean> listSubjectName;
    String path;

    public VideoSubjectTopicsVideoAdapter(Context context, ArrayList<UpdateBean.DataBean> listSubjectName,String path) {
        this.context = context;
        this.path = path;
        this.listSubjectName = listSubjectName;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_subject_topics__videos_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.title.setText("Title" + " :- " + listSubjectName.get(position).getTitle());
        holder.discrption.setText(listSubjectName.get(position).getFilename().get(0).getFile());
        holder.date.setText("Post On" + " :- " + Utils.DateFormate(listSubjectName.get(position).getCreated_at()));
      //  Utils.retrieveVideoFrameFromVideo(holder.videoProgressBar, holder.videoview, listSubjectName.get(position).getFilename().get(0).getFile());
        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent mIntent = ExoPlayerActivity.getStartIntent(context, listSubjectName.get(position).getFilename().get(0).getFile());
                context.startActivity(new Intent(context, VideoPlayer.class).putExtra("url",listSubjectName.get(position).getFilename().get(0).getFile()));
            }
        });
    }

    public int getItemCount() {
        return listSubjectName.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView title, discrption, date;
        CardView message;

        ImageView videoview, imageview_play;
        ProgressBar videoProgressBar;
        FrameLayout rl_video_player;

        public VideoViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            discrption = (TextView) itemView.findViewById(R.id.discrption);
            date = (TextView) itemView.findViewById(R.id.date);
            message = (CardView) itemView.findViewById(R.id.message);


//            videoview = (ImageView) itemView.findViewById(R.id.videoview);
          //  imageview_play = (ImageView) itemView.findViewById(R.id.imageview_play);
          //  videoProgressBar = (ProgressBar) itemView.findViewById(R.id.videoProgressBar);
           // rl_video_player = (FrameLayout) itemView.findViewById(R.id.rl_video_player);
        }
    }
}
