package com.example.androidtraining2_08_1912120208.utils;


import java.io.File;
import java.net.URL;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpManager {

    public static void get(String url, okhttp3.Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //json
    public static void postJ(String url, String json, okhttp3.Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody
                .create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //表单
    public static void postF(String url, Map<String, String> map, okhttp3.Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key, map.get(key));
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //上传图片
    public static void postFile(URL url, File file_1, okhttp3.Callback callback) {
        //上传文件
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file_1);
        MultipartBody body1 = new
                MultipartBody.Builder().addFormDataPart("file", file_1.getName(), body).build();
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(body1)//传递请求体
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //上传图片和数据
    public static void postFileandData(URL url, File file_1, Map<String, String> map, okhttp3.Callback callback) {
        //上传文件
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file_1);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("file", file_1.getName(), body);
        for (String key : map.keySet()) {
            builder.addFormDataPart(key, map.get(key));
        }
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(builder.build())//传递请求体
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
