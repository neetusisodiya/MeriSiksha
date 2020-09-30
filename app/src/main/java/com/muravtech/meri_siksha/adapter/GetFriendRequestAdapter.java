package com.muravtech.meri_siksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.GetFriendRequestList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GetFriendRequestAdapter extends RecyclerView.Adapter<GetFriendRequestAdapter.ViewHolder> {
    protected ItemListener mListener;
    Context mContext;
    ArrayList<GetFriendRequestList.DataBean> getfriend;

    public GetFriendRequestAdapter(Context context, ArrayList<GetFriendRequestList.DataBean> getfriend, ItemListener itemListener) {
        mContext = context;
        mListener = itemListener;
        this.getfriend = getfriend;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_friend_request, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (getfriend.get(position).getImage() != null && !getfriend.get(position).getImage().equalsIgnoreCase("")) {

            Picasso.with(mContext).load(getfriend.get(position).getImage()).into(holder.UserImg);
            holder.UserName.setText(getfriend.get(position).getName());
            holder.tvAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position, "accept");

                }
            });
            holder.tvDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position, "decline");
                }
            });
        }
    }

        @Override
        public int getItemCount () {
            return getfriend.size();
        }

        public void setdata (ArrayList < GetFriendRequestList.DataBean > getFriendRequestLists) {
            getfriend = getFriendRequestLists;
            notifyDataSetChanged();
        }

        public interface ItemListener {
            void onItemClick(int pos, String type);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.UserImg)
            CircleImageView UserImg;
            @BindView(R.id.UserName)
            TextView UserName;
            @BindView(R.id.tv_accept)
            TextView tvAccept;
            @BindView(R.id.tv_decline)
            TextView tvDecline;


            public ViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);

            }


        }
    }
