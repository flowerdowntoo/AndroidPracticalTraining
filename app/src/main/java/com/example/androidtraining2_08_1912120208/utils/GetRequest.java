package com.example.androidtraining2_08_1912120208.utils;

import androidx.lifecycle.LiveData;


import com.example.androidtraining2_08_1912120208.bean.NewsBean;
import com.example.androidtraining2_08_1912120208.bean.PythonBean;
import com.example.androidtraining2_08_1912120208.bean.VideoBean;
import com.github.leonardoxh.livedatacalladapter.Resource;

import java.util.List;

import retrofit2.http.GET;

//Get请求
//得到字符串
//json解析
public interface GetRequest {

    @GET("home_news_list_data.json")
    LiveData<Resource<List<NewsBean>>> getNewsList();

    @GET("home_ad_list_data.json")
    LiveData<Resource<List<NewsBean>>> getAdList();

    @GET("python_list_data.json")
    LiveData<Resource<List<PythonBean>>> getPythonList();

    @GET("video_list_data.json")
    LiveData<Resource<List<VideoBean>>> getVideoList();
}
