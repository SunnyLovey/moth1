package com.example.zhangjing20181220.model;

import com.example.zhangjing20181220.utils.MyCallBack;

import java.util.Map;

public interface IModel {
    void getData(String path, Map<String,String> params, Class clazz, MyCallBack myCallBack);
}
