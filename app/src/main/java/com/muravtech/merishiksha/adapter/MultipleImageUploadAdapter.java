package com.muravtech.merishiksha.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class MultipleImageUploadAdapter extends RecyclerView.Adapter<MultipleImageUploadAdapter.MainHolder> {
    private Context context;
    private ArrayList<Uri> arrayList;
    OnItemClickListener onItemClickListener;

    public MultipleImageUploadAdapter(Context context, ArrayList<Uri> arrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.multi_image_adapter, parent, false);
        return new MainHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        Glide.with(context)
                .load(arrayList.get(position))
                .into(holder.imageView);
        holder.iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class MainHolder extends RecyclerView.ViewHolder {
        ImageView imageView, iv_cancle;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            iv_cancle = itemView.findViewById(R.id.iv_cancle);
        }
    }

}