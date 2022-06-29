package com.example.androidtraining2_08_1912120208.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("setting",
                    MODE_PRIVATE);
            boolean isFirst = sharedPreferences.getBoolean("isFirst", true);
            //判断是否第一次进入
            if (isFirst) {
                startActivity(new Intent(LauncherActivity.this,
                        IntroActivity.class));
                sharedPreferences.edit().putBoolean("isFirst", false).apply();
            } else {
                startActivity(new Intent(LauncherActivity.this,
                        SplashActivity.class));
            }
            finish();
        }, 1000);
        //图片预加载
        Glide.with(this).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhb" +
                "img.b0.upaiyun.com%2Fff7bf8d0b69c799fa491a934e69f9aacd340897c1f716e-BmqnU3_fw658&re" +
                "fer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fm" +
                "t=auto?sec=1659055817&t=51224fea384de82f6c0f7e8664fd4772").preload();
    }
}