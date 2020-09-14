package com.muravtech.merisiksha.adapter;

import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.MessageHistoryList;

import com.muravtech.merisiksha.chat.ChatActivity;
import com.muravtech.merisiksha.utils.AppPreferences;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ViewHolder> {
    private final String user_id;
    private Activity activity;
    private List<MessageHistoryList.DataBean> chatListModelDataList = new ArrayList<>();

    public//Constructor
    ChatRoomListAdapter(Activity activity) {
        this.activity = activity;
        user_id= AppPreferences.getAccessId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatroomlist_rowitems, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MessageHistoryList.DataBean chatListModelData = chatListModelDataList.get(position);
        if (user_id.equalsIgnoreCase(String.valueOf(chatListModelDataList.get(position).getUser_id()))) {

            holder.chatroomListUserName.setText(chatListModelData.getOther_user_username());

        } else {
            holder.chatroomListUserName.setText(chatListModelData.getUsername());

        }
//        if (chatListModelDataList.get(position).getChat_type().equalsIgnoreCase("single")) {
//
//            if (user_id.equalsIgnoreCase(String.valueOf(chatListModelDataList.get(position).getUser_id()))) {
//
//                holder.chatroomListUserName.setText(chatListModelData.getOther_user_username());
//
//            } else {
//                holder.chatroomListUserName.setText(chatListModelData.getUsername());
//
//            }
//        } else {
//            holder.chatroomListUserName.setText(chatListModelData.getOther_user_username());
//            //if (Common.readUsingSharedPreference(activity, Constant.IS_VIP).equalsIgnoreCase("1")) {
//                if (chatListModelDataList.get(position).getOther_user_profile_pic_thumb() != null && !chatListModelDataList.get(position).getOther_user_profile_pic_thumb().equalsIgnoreCase(""))
//                {
//                    Picasso.with(activity).load(chatListModelDataList.get(position).getOther_user_profile_pic_thumb()).into(holder.chatroomListUserImg);
//            }
//        }
        holder.chatroomListLastmessageTxt.setText(chatListModelData.getMessage());

        try {
//            2018-07-10 06:47:55
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df2.setTimeZone(TimeZone.getTimeZone("UTC"));
//
            Date chatDate = null;
            if (chatListModelDataList.get(position).getUpdated_at() != null)
                chatDate = df2.parse(chatListModelDataList.get(position).getUpdated_at());
            else
                chatDate = df2.parse(chatListModelDataList.get(position).getCreated_at());
//            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//            calendar.setTime(chatDate);

            SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Date date1 = new Date();
            Date nowDate = outputFmt.parse(outputFmt.format(date1));

            long diff = nowDate.getTime() - chatDate.getTime();
            long sec = diff / 1000;
            long min = sec / 60;
            long hour = min / 60;
            long days = hour / 24;

            if (days != 0) {
                Log.e("days", "" + days);
                holder.chatroomListTimeStamp.setText(days + activity.getString(R.string.days_ago));
            } else if (hour != 0) {
                Log.e("hour", "" + hour);
                holder.chatroomListTimeStamp.setText(hour + activity.getString(R.string.hours_ago));
            } else if (min != 0) {
                Log.e("min", "" + min);
                holder.chatroomListTimeStamp.setText(min + activity.getString(R.string.min_ago));
            } else {
                Log.e("sec", "" + sec);
                if (sec < 1) {
                    holder.chatroomListTimeStamp.setText(R.string.just_ago);

                } else {
                    holder.chatroomListTimeStamp.setText(sec + activity.getString(R.string.sec_ago));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.chatroomListLayout.setTag(position);
        //Onlick on chatlayout
        holder.chatroomListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();

                Intent intent = new Intent(activity, ChatActivity.class);
                //if (chatListModelDataList.get(position).getChat_type().equalsIgnoreCase("single")) {
                    if (user_id.equalsIgnoreCase(String.valueOf(chatListModelDataList.get(position).getUser_id()))) {
                        intent.putExtra("user_id", chatListModelDataList.get(pos).getOther_user_id() + "");
                        intent.putExtra("name", chatListModelDataList.get(pos).getOther_user_username());
                        intent.putExtra("image", chatListModelDataList.get(pos).getOther_user_profile_pic_thumb());
                        intent.putExtra("gender", chatListModelDataList.get(pos).getOther_gender());
                        intent.putExtra("receiver_type", chatListModelDataList.get(pos).getReceiver_type());
                        intent.putExtra("type", "single");

                    } else {
                        intent.putExtra("user_id", chatListModelDataList.get(pos).getUser_id() + "");
                        intent.putExtra("name", chatListModelDataList.get(pos).getUsername());
                        intent.putExtra("image", chatListModelDataList.get(pos).getProfile_pic_thumb());
                        intent.putExtra("gender", chatListModelDataList.get(pos).getGender());
                        intent.putExtra("receiver_type", chatListModelDataList.get(pos).getReceiver_type());
                        intent.putExtra("type", "single");
                    }
//                } else {
//                    intent.putExtra("user_id", chatListModelDataList.get(pos).getGroup_id() + "");
//                    intent.putExtra("name", chatListModelDataList.get(pos).getOther_user_username());
//                    intent.putExtra("image", chatListModelDataList.get(pos).getOther_user_profile_pic_thumb());
//                    intent.putExtra("gender", "");
//                    intent.putExtra("type", "Group");
//                }
                activity.startActivity(intent);


            }
        });

        //Onlcilk on userimage
        holder.chatroomListUserImg.setTag(position);

        holder.chatroomListUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos = (int) view.getTag();
                if (chatListModelDataList.get(position).getChat_type().equalsIgnoreCase("single")) {
                    String other_user_id;
                    if (user_id.equalsIgnoreCase(String.valueOf(chatListModelDataList.get(position).getUser_id()))) {
                        other_user_id = chatListModelDataList.get(pos).getOther_user_id() + "";
                    } else {
                        other_user_id = chatListModelDataList.get(pos).getUser_id() + "";
                    }
                 //   activity.startActivity(new Intent(activity, FriendListDetailActivity.class).putExtra("user_id", other_user_id));
                } else {
                   // activity.startActivity(new Intent(activity, GroupInfoActivity.class).putExtra("group_id", chatListModelDataList.get(position).getGroup_id() + ""));
                }
            }
        });

        //set data to view

    }

    @Override
    public int getItemCount() {
        return chatListModelDataList.size();
    }

    public void setData(List<MessageHistoryList.DataBean> data) {
        this.chatListModelDataList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView chatroomListUserImg;
        TextView chatroomListUserName;
        TextView chatroomListLastmessageTxt;
        TextView chatroomListTimeStamp;
        ImageView chatroomListOnlineImg;
        private LinearLayout chatroomListLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            chatroomListUserName = itemView.findViewById(R.id.chatroomListUserName);
            chatroomListLastmessageTxt = itemView.findViewById(R.id.chatroomListLastmessageTxt);
            chatroomListTimeStamp =itemView.findViewById(R.id.chatroomListTimeStamp);
            chatroomListLayout = (LinearLayout) itemView.findViewById(R.id.chatroomListLayout);
            chatroomListUserImg =  itemView.findViewById(R.id.chatroomListUserImg);
        }
    }
}
