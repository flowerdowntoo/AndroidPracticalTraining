package com.example.androidtraining2_08_1912120208.ui.video;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.androidtraining2_08_1912120208.bean.NewsBean;
import com.example.androidtraining2_08_1912120208.bean.VideoBean;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.github.leonardoxh.livedatacalladapter.Resource;

import java.util.List;

public class VideoViewModel extends ViewModel {

    //得到广告列表
    public LiveData<List<VideoBean>> getVideoList(){
        return Transformations.map(NetUtils.get().getVideoList(), Resource::getResource);
    }
}