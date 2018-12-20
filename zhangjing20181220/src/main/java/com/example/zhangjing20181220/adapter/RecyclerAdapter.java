package com.example.zhangjing20181220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhangjing20181220.R;
import com.example.zhangjing20181220.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<GoodsBean.DataBean> list;
    private Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<GoodsBean.DataBean> list) {
        this.list .clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addList(List<GoodsBean.DataBean> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_goods,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
         viewHolder.textView_title.setText(list.get(i).getTitle());
         viewHolder.textView_barginPrice.setText("￥"+list.get(i).getPrice()+"");
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(viewHolder.imageView);
       //加入购物车
        viewHolder.button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(callBackListeren!=null){
                   callBackListeren.getDataBack(list.get(i).getPid());
               }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack!=null){
                    callBack.getPosition(list.get(i).getPid(),i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView_title,textView_barginPrice;
        Button button_join;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.images);
            textView_barginPrice=itemView.findViewById(R.id.textView_bargainPrice);
            textView_title=itemView.findViewById(R.id.textView_title);
            button_join=itemView.findViewById(R.id.button_join);
        }
    }
    public onCallBackListeren callBackListeren;

    public void setonCallBackListeren(onCallBackListeren onCallBackListeren){
        this.callBackListeren=onCallBackListeren;
    }
    public interface onCallBackListeren{
        void getDataBack(int position);
    }


    public onCallBack callBack;
    public void setOnCallBack(onCallBack myCallBack){
        this.callBack=myCallBack;
    }

    public interface onCallBack{
        void getPosition(int pid,int position);
    }

}
