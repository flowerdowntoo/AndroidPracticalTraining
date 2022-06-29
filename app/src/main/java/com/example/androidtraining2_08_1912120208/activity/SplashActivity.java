package com.example.androidtraining2_08_1912120208.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.androidtraining2_08_1912120208.R;

public class SplashActivity extends AppCompatActivity {

    private boolean isSkip;
    private boolean isBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView=findViewById(R.id.imageView);
        //加载网络图片
        Glide.with(this).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fff7bf8d0b69c799fa491a934e69f9aacd340897c1f716e-BmqnU3_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659055817&t=51224fea384de82f6c0f7e8664fd4772").into(imageView);

        new Handler().postDelayed(() -> {

            if (!isSkip && !isBack) {
              Intent intent =new Intent(SplashActivity.this,MainActivity.class);
              startActivity(intent);
                finish();
            }

        },5000);
    }

    public void skip(View view) {
        isSkip=true;
        Intent intent =new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    //是否返回
    public void onBackPressed() {
        super.onBackPressed();
        isBack=true;
    }
}