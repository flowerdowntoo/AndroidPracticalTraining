package com.example.androidtraining2_08_1912120208.base;

import androidx.multidex.MultiDexApplication;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.common.BaiduMapSDKException;

import cn.bmob.v3.Bmob;

public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        Bmob.initialize(this, "2690a20c4292bd85606aa655f811f9e5");

        // 是否同意隐私政策，默认为false
        SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
        try {
            // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
            SDKInitializer.initialize(getApplicationContext());
            SDKInitializer.setCoordType(CoordType.BD09LL);
        } catch (BaiduMapSDKException e) {
        }
    }
}
