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

        addSlide(AppIntroFragment.newInstance("极速租车",
                "极速租车",R.drawable.car1));
        addSlide(AppIntroFragment.newInstance("极速租车",
                "极速租车", R.drawable.car2));
        addSlide(AppIntroFragment.newInstance("极速租车",
                "极速租车", R.drawable.car3));
        addSlide(AppIntroFragment.newInstance("极速租车",
                "极速租车", R.drawable.car4));
        addSlide(AppIntroFragment.newInstance("极速租车",
                "极速租车", R.drawable.car5));
        addSlide(AppIntroFragment.newInstance("极速租车",
                "极速租车", R.drawable.car6));
        addSlide(AppIntroFragment.newInstance("极速租车",
                "极速租车", R.drawable.car7));
        addSlide(AppIntroFragment.newInstance("极速租车",
                "极速租车", R.drawable.car8));
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
