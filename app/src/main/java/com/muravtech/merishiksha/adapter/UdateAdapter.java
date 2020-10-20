package com.muravtech.merishiksha.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merishiksha.Bean.UpdateBean;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.interfaces.OnItemClickListener;
import com.muravtech.merishiksha.utils.Utils;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neetu on 4/6/20.
 */
public class UdateAdapter extends RecyclerView.Adapter<UdateAdapter.ViewHolder> {
    private Context mContext;
    private List<UpdateBean.DataBean> mData;
    String path;
    OnItemClickListener onItemClickListener;

    public UdateAdapter(Context mContext, List<UpdateBean.DataBean> mData,String path,OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.path = path;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.viewholder_list_item_suggetion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UpdateBean.DataBean item = mData.get(position);

        if (item.getType().equalsIgnoreCase("image")) {
            holder.sliderView.setVisibility(View.VISIBLE);
            holder.textView_title.setText(item.getTitle());

            SliderAdapterExample adapter = new SliderAdapterExample(mContext, item.getFilename(), "sort",path,onItemClickListener,position);
            holder.sliderView.setSliderAdapter(adapter);

         //   holder.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            holder.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            holder.sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
            holder.sliderView.setIndicatorSelectedColor(R.color.colorPrimary);
            holder.sliderView.setIndicatorUnselectedColor(Color.GRAY);
            holder.sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
          //  holder.sliderView.startAutoCycle();


            if (item.getCreated_at() != null)
                holder.textview_date.setText(Html.fromHtml(Utils.DateFormate(item.getCreated_at())));
        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_get)
        SliderView sliderView;
        @BindView(R.id.textView_title)
        TextView textView_title;
        @BindView(R.id.textview_date)
        TextView textview_date;
        @BindView(R.id.rl_main)
        RelativeLayout rl_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}

