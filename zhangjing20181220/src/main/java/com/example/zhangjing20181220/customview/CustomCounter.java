package com.example.zhangjing20181220.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zhangjing20181220.R;
import com.example.zhangjing20181220.adapter.RecyclerGoodsAdapter;
import com.example.zhangjing20181220.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class CustomCounter extends LinearLayout {
    private Context mContext;
    private ImageView imageView_jia;
    private ImageView imageView_jian;
    private EditText editText_num;
    private RecyclerGoodsAdapter adapter;
    private int num;
    private int position;
    private List<ShopBean.Data.ListBean> list;

    public CustomCounter(Context context) {
        super(context);
        mContext=context;
        list=new ArrayList<>();
        initView();
    }

    public CustomCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        list=new ArrayList<>();
        initView();
    }
    public void setData(RecyclerGoodsAdapter adapter, final List<ShopBean.Data.ListBean> list, int i){
        this.adapter=adapter;
        this.list=list;
        position=i;
         num = list.get(i).getNum();
         editText_num.setText(num+"");
         editText_num.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 try {
                     String s1 = String.valueOf(s);
                     num = Integer.parseInt(s1);
                     list.get(position).setNum(num);

                 }catch (Exception e){
                     list.get(position).setNum(1);
                 }
                 if(callBackListener!=null){
                     callBackListener.getCheckState();
                 }
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });
    }

    private void initView() {
        View view=View.inflate(mContext, R.layout.layout_counter,null);
        imageView_jia = view.findViewById(R.id.image_jia);
        imageView_jian = view.findViewById(R.id.image_jian);
        editText_num = view.findViewById(R.id.edit_num);
        addView(view);

        imageView_jia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                editText_num.setText(num+"");
                list.get(position).setNum(num);
                callBackListener.getCheckState();
                adapter.notifyItemChanged(position);
            }
        });

        imageView_jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num>1){
                    num--;
                    editText_num.setText(num+"");
                    list.get(position).setNum(num);
                    callBackListener.getCheckState();
                    adapter.notifyItemChanged(position);
                }else {
                    Toast.makeText(mContext,"数量不能小于1",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public onCallBackListener callBackListener;
    public void setOnCallBackListener(onCallBackListener backListener){
        this.callBackListener=backListener;
    }

    public interface onCallBackListener{
        void getCheckState();
    }
}
