package com.example.androidtraining2_08_1912120208.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.example.androidtraining2_08_1912120208.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.bkcolor);
        this.getWindow().setBackgroundDrawable(drawable);

        addSlide(AppIntroFragment.newInstance("第一日",
                "启动页、引导页、闪屏页与基础框架搭建",R.drawable.p1));
        addSlide(AppIntroFragment.newInstance("第二日",
                "LiveData+Retrofit网络请求与下拉刷新SmartRefreshLayout", R.drawable.p2));
        addSlide(AppIntroFragment.newInstance("第三日",
                "RecyclerAdapter框架与轮播图banner", R.drawable.p3));
        addSlide(AppIntroFragment.newInstance("第四日",
                "新闻详情页AgentWeb与Python详情页", R.drawable.p4));
        addSlide(AppIntroFragment.newInstance("第五日",
                "爆炸菜单BoomMenu与统计图表MPAndroidChart", R.drawable.p5));
        addSlide(AppIntroFragment.newInstance("第六日",
                "视频列表与视频播放器GSYVideoPlayer", R.drawable.p6));
        addSlide(AppIntroFragment.newInstance("第七日",
                "我的界面与基于Bmob后端云的登录注册，找回密码", R.drawable.p7));
        addSlide(AppIntroFragment.newInstance("第八日",
                "百度地图API", R.drawable.p8));
        setSkipText("跳过");
        setDoneText("完成");

        setImmersiveMode();
    }

    @Override
    //跳过
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    //完成
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
