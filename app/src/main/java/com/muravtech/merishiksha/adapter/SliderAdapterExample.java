package com.muravtech.merishiksha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.muravtech.merishiksha.Bean.UpdateBean;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.interfaces.OnItemClickListener;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<UpdateBean.DataBean.FilenameBean> mSliderItems;
    String path;
    String type;
    OnItemClickListener onItemClickListener;
    int position;

    public SliderAdapterExample(Context context, List<UpdateBean.DataBean.FilenameBean> sliderItems, String type, String path) {
        this.context = context;
        this.mSliderItems = sliderItems;
        this.path = path;
        this.type = type;
    }

    public SliderAdapterExample(Context context, List<UpdateBean.DataBean.FilenameBean> sliderItems, String type, String path,OnItemClickListener onItemClickListener,int position) {
        this.context = context;
        this.mSliderItems = sliderItems;
        this.path = path;
        this.type = type;
        this.position = position;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_pager, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position1) {

        Picasso.with(context).load(path+mSliderItems.get(position1).getFile()).placeholder(R.mipmap.siksha).into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equalsIgnoreCase("sort")) {
                    onItemClickListener.onClick(position);
                    //Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        // ImageView imageGifContainer;
        //TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.eventpicture);
            //imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            //textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }



}

