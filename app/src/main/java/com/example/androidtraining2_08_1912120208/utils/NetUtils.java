package com.example.androidtraining2_08_1912120208.utils;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//创建网络工具类
public class NetUtils {
    public static final String BASE_URL = "http://2155a780.cpolar.cn/topline/";
    public static final String INTERNET_THROUGH_URL = "http://33b1a7c4.cpolar.cn/";
    public static GetRequest get(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GetRequest.class);
    }
}
