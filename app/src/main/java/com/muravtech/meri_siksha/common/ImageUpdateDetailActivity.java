package com.muravtech.meri_siksha.common;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.muravtech.meri_siksha.Bean.UpdateBean;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.adapter.SliderAdapterExample;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by htl on 11/6/16.
 */
public class ImageUpdateDetailActivity extends AppCompatActivity {
    @BindView((R.id.image_get))
    SliderView sliderView;
    String path;
    UpdateBean.DataBean file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);
        ButterKnife.bind(this);
        path = getIntent().getStringExtra("path");
        file = (UpdateBean.DataBean) getIntent().getSerializableExtra("file");

        SliderAdapterExample adapter = new SliderAdapterExample(this, file.getFilename(),"full", path);

        sliderView.setSliderAdapter(adapter);

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setIndicatorSelectedColor(R.color.colorPrimary);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();



    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
