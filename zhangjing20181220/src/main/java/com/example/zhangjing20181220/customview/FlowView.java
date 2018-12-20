package com.example.zhangjing20181220.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class FlowView extends LinearLayout {
    private int mChildrenMaxHeight;
    private int mLeft=20;
    private int mTop=20;

    public FlowView(Context context) {
        super(context);
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //定义父窗口的大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //测量孩子的大小
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //找到最高的孩子
        findChildrenMaxHeight();
        int left=0,top=0;
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if(left!=0){
                if((left+view.getMeasuredWidth())>widthSize){
                    top+=mChildrenMaxHeight+mTop;
                    left=0;
                }
            }
            left+=view.getMeasuredWidth()+mLeft;
        }
        setMeasuredDimension(widthSize,(top+mChildrenMaxHeight)>heightSize ? heightSize : top+mChildrenMaxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //找到最高的孩子
        findChildrenMaxHeight();
        int left=0,top=0;
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if(left!=0){
                if((left+view.getMeasuredWidth())>getWidth()){
                    top+=mChildrenMaxHeight+mTop;
                    left=0;
                }
            }
           view.layout(left,top,left+view.getMeasuredWidth(),top+mChildrenMaxHeight);
            left+=view.getMeasuredWidth()+mLeft;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    public void findChildrenMaxHeight(){
        mChildrenMaxHeight=0;
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if(mChildrenMaxHeight<view.getMeasuredHeight()){
                mChildrenMaxHeight=view.getMeasuredHeight();
            }
        }
    }
}
