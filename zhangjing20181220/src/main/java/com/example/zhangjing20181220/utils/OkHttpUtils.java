package com.example.zhangjing20181220.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils instance;
    private OkHttpClient client;
    private Handler handler=new Handler(Looper.getMainLooper());
    public static OkHttpUtils getInstance(){
        if(instance==null){
            synchronized (OkHttpUtils.class){
                if(null==instance){
                    instance=new OkHttpUtils();
                }
            }
        }
        return instance;
    }
    private OkHttpUtils(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor interceptor1 = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client=new OkHttpClient.Builder()
                .addInterceptor(interceptor1)//添加拦截器
                .writeTimeout(5000, TimeUnit.SECONDS)//写超时
                .readTimeout(5000,TimeUnit.SECONDS)//读超时
                .connectTimeout(5000,TimeUnit.SECONDS)//连接超时
                .build();
    }
    //异步请求post
    public void postRequest(String path, Map<String,String> params, final Class clazz, final MCallBack mCallBack){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> entry:params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body=builder.build();
        Request request = new Request.Builder()
                .post(body)
                .url(path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mCallBack.onFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String request = response.body().string();
                    Gson gson=new Gson();
                    final Object o = gson.fromJson(request, clazz);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallBack.onSuccessed(o);
                        }
                    });

                }catch (Exception e){
                    mCallBack.onFailed(e);
                }
            }
        });
    }
}
