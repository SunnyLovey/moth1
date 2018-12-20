package com.example.zhangjing20181220.customview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhangjing20181220.MainActivity;
import com.example.zhangjing20181220.R;
import com.example.zhangjing20181220.activity.SuccessActivity;

public class HeadView extends LinearLayout {
    private Context mContext;
    private ImageView imageView_search;
    private EditText editText_name;
    private TextView textView_forword;

    public HeadView(Context context) {
        super(context);
        mContext=context;
        initView();
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView();
    }

    private void initView() {
        //加载布局
        View view=View.inflate(mContext, R.layout.layout_head_view,null);
        //获取资源ID
        imageView_search = view.findViewById(R.id.image_search);
        editText_name = view.findViewById(R.id.edit_name);
        textView_forword = view.findViewById(R.id.text_forword);

        //点击事件
        imageView_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallBack!=null){
                    mCallBack.getEdit(editText_name.getText().toString());
                }
            }
        });
        addView(view);
        //跳转
        textView_forword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, SuccessActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
    //接口回调
    private CallBack mCallBack;
    public void setOnCallBack(CallBack myCallBack){
        this.mCallBack=myCallBack;
    }
    public interface CallBack{
        void getEdit(String name);
    }
}
