package com.muravtech.merisiksha.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;

import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.muravtech.merisiksha.R;


/**
 * Created by kapoor.saini on 4/28/2017.
 */

@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {
    Context context;
    String fName;
    Typeface typeface;

    public CustomTextView(Context context) {
        super(context);
        this.context = context;
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        try {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TextViewFont);
            fName = attributes.getString(R.styleable.TextViewFont_fontName);

//            if (fName != null) {
//
//                if (fName.equalsIgnoreCase("DancingScript_Regular")) {
//                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/DancingScript-Regular.ttf");
//                } else if (fName.equalsIgnoreCase("Lato_Bold")) {
//                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");
//                } else if (fName.equalsIgnoreCase("Lato_Regular")) {
//                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");
//                } else if (fName.equalsIgnoreCase("Raleway_SemiBold")) {
//                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-SemiBold.ttf");
//                } else if (fName.equalsIgnoreCase("SF_Cartoonist_Hand")) {
//                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SF_Cartoonist_Hand.ttf");
//                } else if (fName.equalsIgnoreCase("SF_Cartoonist_Hand_Bold")) {
//                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SF_Cartoonist_Hand_Bold.ttf");
//                } else if (fName.equalsIgnoreCase("Rage_Italic")) {
//                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Italianno.ttf");
//                }
//
//                if (typeface != null) {
//                    this.setTypeface(typeface);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
