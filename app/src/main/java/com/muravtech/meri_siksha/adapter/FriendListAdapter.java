package com.muravtech.meri_siksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.teacher.FriendList;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.VideoViewHolder> {
    Context context;
    ArrayList<FriendList.DataBean> listSubjectName;
    OnItemClickListener onItemClickListener;


    public FriendListAdapter(Context context, ArrayList<FriendList.DataBean> listSubjectName, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listSubjectName = listSubjectName;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.search_friend_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        holder.chatroomUserName.setText(listSubjectName.get(position).getName());
        if(listSubjectName.get(position).getImage()!=null && !listSubjectName.get(position).getImage().equalsIgnoreCase("")){
            Picasso.with(context).load(listSubjectName.get(position).getImage()).placeholder(R.drawable.defaultpic).into(holder.chatroomUserImg);
        }

        if(listSubjectName.get(position).getRelation_status()!=null && listSubjectName.get(position).getRelation_status().equalsIgnoreCase("1")){
            holder.chatroomAddFriend.setVisibility(View.GONE);
        }
        holder.chatroomAddFriend.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.chatroomUserImg)
        CircleImageView chatroomUserImg;
        @BindView(R.id.chatroomUserName)
        TextView chatroomUserName;
        @BindView(R.id.chatroom_add_friend)
        TextView chatroomAddFriend;

        @BindView(R.id.chatroom_decline)
        TextView chatroomDecline;
        @BindView(R.id.chatroom_accept)
        TextView chatroomAccept;
//        @BindView(R.id.chatroomListLayout)
//        RelativeLayout chatroomListLayout;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }
}
