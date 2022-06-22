package com.example.androidtraining2_08_1912120208.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.androidtraining2_08_1912120208.bean.NewsBean;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.github.leonardoxh.livedatacalladapter.Resource;

import java.util.List;

public class HomeViewModel extends ViewModel {


    //得到新闻列表
    public LiveData<List<NewsBean>> getNewsList() {
        return Transformations.map(NetUtils.get().getNewsList(), Resource::getResource);
    }

    //得到广告列表
    public LiveData<List<NewsBean>> getAdList() {
        return Transformations.map(NetUtils.get().getAdList(), Resource::getResource);
    }
}