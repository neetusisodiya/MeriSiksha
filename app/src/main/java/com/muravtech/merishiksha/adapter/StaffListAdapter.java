package com.muravtech.merishiksha.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merishiksha.Response.StaffListDetailBean;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.interfaces.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by neetu on 25/06/2020.
 */
public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<StaffListDetailBean.DataBean> mData;
    List<StaffListDetailBean.DataBean> mStringFilterList;
    OnItemClickListener onItemClickListener;

    public StaffListAdapter(Context mContext, ArrayList<StaffListDetailBean.DataBean> mData,OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        mStringFilterList=mData;
        this.onItemClickListener=onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StaffListAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_groupchild, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StaffListDetailBean.DataBean item = mData.get(position);

        holder.tv_username.setText(Html.fromHtml(item.getName()));
        holder.tv_class.setText(item.getContact_no());
        Picasso.with(mContext).load(item.getProfile_pic()).placeholder(R.drawable.defaultpic).into(holder.iv_child);

        holder.tv_roll_no.setVisibility(View.GONE);
        holder.iv_check.setVisibility(View.GONE);
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mData = mStringFilterList;
                } else {
                    List<StaffListDetailBean.DataBean> filteredList = new ArrayList<>();
                    for (StaffListDetailBean.DataBean row : mStringFilterList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mData = (ArrayList<StaffListDetailBean.DataBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username,tv_roll_no,tv_class;
        CircleImageView iv_child;
        RelativeLayout ll_main;
        CheckBox iv_check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_child = (CircleImageView) itemView.findViewById(R.id.iv_child);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            ll_main = (RelativeLayout) itemView.findViewById(R.id.ll_main);
            iv_check =  itemView.findViewById(R.id.iv_check);
            tv_roll_no =  itemView.findViewById(R.id.tv_roll_no);
            tv_class =  itemView.findViewById(R.id.tv_class);
        }
    }

}