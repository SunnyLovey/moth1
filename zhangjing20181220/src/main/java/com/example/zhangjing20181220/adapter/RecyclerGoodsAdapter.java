package com.example.zhangjing20181220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhangjing20181220.R;
import com.example.zhangjing20181220.bean.ShopBean;
import com.example.zhangjing20181220.customview.CustomCounter;

import java.util.List;

public class RecyclerGoodsAdapter extends RecyclerView.Adapter<RecyclerGoodsAdapter.ViewHolder> {
    private List<ShopBean.Data.ListBean> list;
    private Context context;

    public RecyclerGoodsAdapter(List<ShopBean.Data.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_goods_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView_title.setText(list.get(i).getTitle());
        viewHolder.textView_price.setText("￥"+list.get(i).getBargainPrice()+"");
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(viewHolder.imageView);
        viewHolder.checkBox_goods.setChecked(list.get(i).isCheck);
        viewHolder.checkBox_goods.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(i).setCheck(isChecked);
                if(callBackListener!=null){
                    callBackListener.getCheckState();
                }
            }
        });

       viewHolder.customCounter.setData(this,list,i);
       viewHolder.customCounter.setOnCallBackListener(new CustomCounter.onCallBackListener() {
           @Override
           public void getCheckState() {
               if(callBackListener!=null){
                   callBackListener.getCheckState();
               }
           }
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox_goods;
        ImageView imageView;
        TextView textView_title,textView_price;
        CustomCounter customCounter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox_goods=itemView.findViewById(R.id.checkBox_goods);
            imageView=itemView.findViewById(R.id.imageView_image);
            textView_price=itemView.findViewById(R.id.textView_goods_price);
            textView_title=itemView.findViewById(R.id.textView_goods_title);
            customCounter=itemView.findViewById(R.id.custom_counter);
        }
    }
    public onCallBackListener callBackListener;
    public void setOnCallBackListener(onCallBackListener backListener){
        this.callBackListener=backListener;
    }

    public interface onCallBackListener{
        void getCheckState();
    }
public void updateCheck(boolean isSelect){
        for (ShopBean.Data.ListBean listBean:list){
            listBean.setCheck(isSelect);
        }
        notifyDataSetChanged();
}

}




