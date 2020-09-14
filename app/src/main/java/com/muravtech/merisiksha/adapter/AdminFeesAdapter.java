package com.muravtech.merisiksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.Bean.Paidfee;
import com.muravtech.merisiksha.R;

import java.util.ArrayList;
import java.util.List;


public class AdminFeesAdapter extends RecyclerView.Adapter<AdminFeesAdapter.ViewHolder> {
    private Context mContext;
    private List<Paidfee.DataBean> mData;

        public AdminFeesAdapter(Context mContext, ArrayList<Paidfee.DataBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminFeesAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_paid_student, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Paidfee.DataBean item = mData.get(position);

        holder.textView_name.setText("Demo");
        holder.textView_totalfess.setText(item.getTotal_fee());
        holder.textView_paidfess.setText(item.getPaid_fee());
        holder.textView_duefess1.setText(item.getDue());
    }

    @Override
    public int getItemCount() {
        return  mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name,textView_totalfess,textView_paidfess,textView_duefess1;

        public ViewHolder(@NonNull View row) {
            super(row);

            textView_name = (TextView) row.findViewById(R.id.textView_name);
            textView_paidfess = (TextView) row.findViewById(R.id.textView_paidfess);
            textView_totalfess = (TextView) row.findViewById(R.id.textView_totalfess);
            textView_duefess1 = (TextView) row.findViewById(R.id.textView_duefess);
        }
    }
}