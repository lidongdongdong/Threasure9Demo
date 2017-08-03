package com.zhuoxin.com.threasure9demo.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/2.
 */

public class NetClient {
    public static  final String BASE_URL="http://admin.syfeicuiedu.com";
    private static NetClient netClient;
    private final Retrofit retrofit;
    private NetRequest netRequest;
    private NetClient(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();
        retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public  static  synchronized NetClient getInstance(){
        if (netClient==null){
            netClient=new NetClient();
        }
        return netClient;
    }
    public NetRequest getNetRequest(){
        if (netRequest==null){
            netRequest=retrofit.create(NetRequest.class);
        }
        return netRequest;
    }
}
