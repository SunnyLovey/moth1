package com.example.zhangjing20181220.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.zhangjing20181220.R;

public class CustomText extends android.support.v7.widget.AppCompatTextView {
    public CustomText(Context context) {
        super(context);
    }

    public CustomText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
        int color = typedArray.getColor(R.styleable.HeadView_textColor, Color.GRAY);
        setTextColor(color);//设置颜色
        //回收
        typedArray.recycle();
    }


}
