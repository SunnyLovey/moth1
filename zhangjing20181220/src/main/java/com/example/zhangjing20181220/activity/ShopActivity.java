package com.example.zhangjing20181220.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.zhangjing20181220.R;
import com.example.zhangjing20181220.adapter.RecyclerViewListAdapter;
import com.example.zhangjing20181220.bean.ShopBean;
import com.example.zhangjing20181220.presenter.IPresenter;
import com.example.zhangjing20181220.presenter.IPresenterImpl;
import com.example.zhangjing20181220.utils.Path;
import com.example.zhangjing20181220.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopActivity extends AppCompatActivity implements IView{
    private IPresenterImpl iPresenter;
    private RecyclerView recyclerView_list;
    private RecyclerViewListAdapter adapter;
    private CheckBox checkBox_all;
    private TextView textView;
    private List<ShopBean.Data> data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        iPresenter=new IPresenterImpl(this);
        initView(savedInstanceState);

        LinearLayoutManager manager=new LinearLayoutManager(ShopActivity.this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_list.setLayoutManager(manager);

        adapter=new RecyclerViewListAdapter(this);
        recyclerView_list.setAdapter(adapter);

        adapter.setOnCallBackListener(new RecyclerViewListAdapter.onCallBackListener() {
            @Override
            public void getCheckState() {
                double priceTotal=0;
                int num=0;
                int totalNum=0;
                for(int a=0;a<data1.size();a++){
                    List<ShopBean.Data.ListBean> list = data1.get(a).getList();
                    for (int i=0;i<list.size();i++){
                        totalNum = list.get(i).getNum()+totalNum;
                        if(list.get(i).isCheck){
                            priceTotal=priceTotal+(list.get(i).getNum()+list.get(i).getBargainPrice());
                            num=num+list.get(i).getNum();
                        }
                    }
                }
                if(num<totalNum){
                    checkBox_all.setChecked(false);
                }else {
                    checkBox_all.setChecked(true);
                }
                textView.setText("总价："+priceTotal);
                checkBox_all.setText("已选（"+num+")");
            }
        });

        checkBox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll(checkBox_all.isChecked());
                adapter.notifyDataSetChanged();
            }
        });
        Map<String,String> params=new HashMap<>();
        params.put("uid",80+"");
        iPresenter.startRequest(Path.path_select_goods,params,ShopBean.class);
    }

    private void initView(Bundle savedInstanceState) {
        recyclerView_list = findViewById(R.id.recycler_list);
        checkBox_all = findViewById(R.id.check_all);
        textView = findViewById(R.id.text_totalprice);

    }

    @Override
    public void requestSuccess(Object data) {
        ShopBean bean= (ShopBean) data;
        data1 = bean.getData();
        data1.remove(0);
        adapter.setList(data1);

    }
    public void checkAll(boolean isSelect){
        double priceTotal=0;
        int num=0;
        for(int a=0;a<data1.size();a++){
            data1.get(a).setCheck(isSelect);
            List<ShopBean.Data.ListBean> list = data1.get(a).getList();
            for (int i=0;i<list.size();i++ ){
                list.get(i).setCheck(isSelect);
                priceTotal=priceTotal+(list.get(i).getNum()+list.get(i).getBargainPrice());
                num=num+list.get(i).getNum();
            }
        }
        if(isSelect){
            textView.setText("总价："+priceTotal);
            checkBox_all.setText("已选（"+num+")");
        }else {
            textView.setText("总价：0.0");
            checkBox_all.setText("已选（"+0+")");
        }
    }
}
