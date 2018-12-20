package com.example.zhangjing20181220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.zhangjing20181220.R;
import com.example.zhangjing20181220.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ViewHolder> {
    private List<ShopBean.Data> list;
    private Context context;

    public RecyclerViewListAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShopBean.Data> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_recycler_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
                viewHolder.textView_seller.setText(list.get(i).getSellerName());

                LinearLayoutManager manager=new LinearLayoutManager(context);
                manager.setOrientation(OrientationHelper.VERTICAL);
                viewHolder.recyclerView.setLayoutManager(manager);
                final RecyclerGoodsAdapter adapter=new RecyclerGoodsAdapter(list.get(i).getList(),context);
                viewHolder.recyclerView.setAdapter(adapter);
                viewHolder.checkBox_seller.setChecked(list.get(i).isCheck);

          adapter.setOnCallBackListener(new RecyclerGoodsAdapter.onCallBackListener() {
              @Override
              public void getCheckState() {
                  if(callBackListener!=null){
                      callBackListener.getCheckState();
                  }
                List<ShopBean.Data.ListBean> list1= list.get(i).getList();
                  boolean isSelectAll=true;
                  for (ShopBean.Data.ListBean listBean:list1){
                      if(!listBean.isCheck){
                          isSelectAll=false;
                          break;
                      }
                  }
                  list.get(i).setCheck(isSelectAll);
                  viewHolder.checkBox_seller.setChecked(isSelectAll);
              }
          });

          viewHolder.checkBox_seller.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                 list.get(i).setCheck(viewHolder.checkBox_seller.isChecked());
                  adapter.updateCheck(viewHolder.checkBox_seller.isChecked());
              }
          });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox_seller;
        TextView textView_seller;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           checkBox_seller=itemView.findViewById(R.id.checkBox_seller);

            textView_seller=itemView.findViewById(R.id.textView_seller);
            recyclerView=itemView.findViewById(R.id.recycler_goods_shop);
        }
    }
    public onCallBackListener callBackListener;

    public void setOnCallBackListener(onCallBackListener backListener){
        this.callBackListener=backListener;
    }

    public interface onCallBackListener{
        void getCheckState();
    }

}
