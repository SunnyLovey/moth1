package com.example.zhangjing20181220.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangjing20181220.MainActivity;
import com.example.zhangjing20181220.R;
import com.example.zhangjing20181220.adapter.RecyclerAdapter;
import com.example.zhangjing20181220.bean.AddGoodsBean;
import com.example.zhangjing20181220.bean.GoodsBean;
import com.example.zhangjing20181220.presenter.IPresenterImpl;
import com.example.zhangjing20181220.utils.Path;
import com.example.zhangjing20181220.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuccessActivity extends AppCompatActivity implements IView{
    private IPresenterImpl iPresenter;
    //2345
    private ImageView imageView_search;
    private EditText editText_name;
    private TextView textView_zh,textView_xl,textView_price;
    private XRecyclerView xRecyclerView;
    private RecyclerAdapter adapter;
    private int mPage;
    private String name;
    private int mSort=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);
        initView(savedInstanceState);
        mPage=1;
        imageView_search.setOnClickListener(new View.OnClickListener() {

            //zj666 ，开发新功能

            @Override
            public void onClick(View v) {
                name = editText_name.getText().toString();
                initData();
                getInfo();

            }
        });
        orderZh();
        orderXl();
        orderPrice();





    }

    private void orderPrice() {
        textView_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPage=1;
                mSort=2;
                getInfo();
            }
        });
    }

    private void orderXl() {
        textView_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPage=1;
                mSort=1;
                getInfo();
            }
        });
    }

    private void orderZh() {
        textView_zh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPage=1;
                mSort=0;
                getInfo();
            }
        });
    }

    private void initData() {
        LinearLayoutManager manager=new LinearLayoutManager(SuccessActivity.this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);

        adapter=new RecyclerAdapter(this);
        xRecyclerView.setAdapter(adapter);

        adapter.setonCallBackListeren(new RecyclerAdapter.onCallBackListeren() {
            @Override
            public void getDataBack(int position) {
                Map<String,String> params=new HashMap<>();
                params.put("uid",80+"");
                params.put("pid",position+"");
                iPresenter.startRequest(Path.path_add_goods,params, AddGoodsBean.class);
            }
        });

       adapter.setOnCallBack(new RecyclerAdapter.onCallBack() {
           @Override
           public void getPosition(int pid, int position) {
               Intent intent=new Intent(SuccessActivity.this,ShopActivity.class);
               startActivity(intent);
           }
       });

        //允许刷新加载
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                getInfo();
            }

            @Override
            public void onLoadMore() {
                getInfo();
            }
        });

    }

    private void getInfo() {
        Map<String,String> params=new HashMap<>();
        params.put("keywords",name);
        params.put("page",String.valueOf(mPage));
        params.put("sort",String.valueOf(mSort));
     iPresenter.startRequest(Path.path_goods,params, GoodsBean.class);
    }

    private void initView(Bundle savedInstanceState) {
        imageView_search = findViewById(R.id.image_success_search);
        editText_name = findViewById(R.id.edit_success_name);
        textView_zh = findViewById(R.id.text_zh);
        textView_xl=findViewById(R.id.text_xl);
        textView_price=findViewById(R.id.text_price);
        xRecyclerView = findViewById(R.id.x_recycler);

    }

    @Override
    public void requestSuccess(Object data) {
       if(data instanceof GoodsBean){
           GoodsBean bean= (GoodsBean) data;
           List<GoodsBean.DataBean> data1 = bean.getData();
           if(mPage==1){
               adapter.setList(data1);
           }else {
               adapter.addList(data1);
           }
           mPage++;
           xRecyclerView.loadMoreComplete();
           xRecyclerView.refreshComplete();
       }else if(data instanceof AddGoodsBean){
           AddGoodsBean bean= (AddGoodsBean) data;
           if(bean.getCode().equals("0")){
               Toast.makeText(SuccessActivity.this,bean.getMsg(),Toast.LENGTH_LONG).show();
           }else{
               Toast.makeText(SuccessActivity.this,bean.getMsg(),Toast.LENGTH_LONG).show();
           }
       }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detachView();
    }
}
