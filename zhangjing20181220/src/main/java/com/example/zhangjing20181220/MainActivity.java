package com.example.zhangjing20181220;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zhangjing20181220.customview.FlowView;
import com.example.zhangjing20181220.customview.HeadView;
import com.example.zhangjing20181220.presenter.IPresenter;
import com.example.zhangjing20181220.presenter.IPresenterImpl;
import com.example.zhangjing20181220.view.IView;

public class MainActivity extends AppCompatActivity {


    private HeadView headView;
    private FlowView flowView,flowView_last;
    private String[] str=new String[]{"短袖男","衬衫","条纹衬衫"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        //dev_zj update feat: 123
        headView.setOnCallBack(new HeadView.CallBack() {
            @Override
            public void getEdit(String name) {
                TextView textView=new TextView(MainActivity.this);
                textView.setText(name);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);
                //textview.settextsize(345);
                textView.setBackgroundResource(R.drawable.shape_text);
                textView.setPadding(10,10,10,10);
                flowView.addView(textView);
                //提交测试
                //哈哈，我更新了
            }
            //修改了
        });
        getFlow();



    }

    private void initView(Bundle savedInstanceState) {
        headView = findViewById(R.id.headView);
        flowView = findViewById(R.id.flow_view_near);
        flowView_last=findViewById(R.id.flow_view_last);
        //张静更新代码了，修改了bug
        //master 更改了
    }
    private void getFlow(){
        for (int i=0;i<str.length;i++){
            TextView textView=new TextView(MainActivity.this);
            textView.setText(str[i]);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(18);
//          textView.setTextSize(18);
            textView.setBackgroundResource(R.drawable.shape_text);
            textView.setPadding(10,10,10,10);
            //更改了bug
            flowView_last.addView(textView);
        }
    }


}
