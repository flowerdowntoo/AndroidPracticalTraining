package com.example.androidtraining2_08_1912120208.ui.me.user;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.base.BaseFragment2;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.datepicker.TimeCount;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class FindPasswordFragment extends BaseFragment2 {
    private EditText editText;
    private EditText editCode;
    private Button findPassword;
    private String email;
    private EditText password;
    private TimeCount  timeCount;
    boolean emailExits = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_find_password, container, false);
        editText=root.findViewById(R.id.editText);
        editCode=root.findViewById(R.id.editCode);
        Button button=root.findViewById(R.id.button);
         findPassword =root.findViewById(R.id.button2);
        button.setOnClickListener(this::resetPasswordByEmail);
        password = root.findViewById(R.id.editTextPassword );
        findPassword.setOnClickListener(this::findPassword);
        timeCount = new TimeCount(60 * 1000+300, 1000,findPassword);
        return root;
    }

    private void findPassword(View view) {

        if(TextUtils.isEmpty(editText.getText())){
            editText.setError("邮箱不可以为空");
            return;
        }

        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/user/checkEmailExist/"+editText.getText();

        OkHttpManager.get(uri, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String responseData = "";
                        Result<String> result = null;
                        try {
                            responseData = response.body().string();
                            System.out.println(responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        try {
                            result = objectMapper.readValue(responseData,new TypeReference<Result<String>>(){});
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        if (result.getCode() == 1){

                            System.out.println("邮箱是"+editText.getText().toString());
                            String uri1= NetUtils.INTERNET_THROUGH_URL+"androidtest/sendEmail/"+editText.getText().toString();


                            OkHttpManager.get(uri1, new Callback() {
                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String responseData = "";
                                            Result<String> result = null;
                                            try {
                                                responseData = response.body().string();
                                                System.out.println(responseData);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            ObjectMapper objectMapper = new ObjectMapper();
                                            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                                            try {
                                                result = objectMapper.readValue(responseData,new TypeReference<Result<String>>(){});
                                            } catch (JsonProcessingException e) {
                                                e.printStackTrace();
                                            }
                                            if (result.getCode() == 1){
                                                email = editText.getText().toString();
                                                Toast.makeText(FindPasswordFragment.this.getContext(), "发送成功!", Toast.LENGTH_SHORT).show();
                                            }else{
                                                email = "";
                                                Toast.makeText(FindPasswordFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }
                            });
                        }else{
                            email="";
                            Toast.makeText(FindPasswordFragment.this.getContext(), "此邮箱未绑定账号!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

        });


            timeCount.start();

    }

    private void changePassword(){
        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/user/modifyPassword";

        Map map = new HashMap();
        map.put("password",password.getText().toString());
        map.put("email",email);

        OkHttpManager.postF(uri, map,new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String responseData = "";
                        Result<String> result = null;
                        try {
                            responseData = response.body().string();
                            System.out.println(responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        try {
                            result = objectMapper.readValue(responseData,new TypeReference<Result<String>>(){});
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        if (result.getCode() == 1){
                            Toast.makeText(FindPasswordFragment.this.getContext(), "发送成功!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FindPasswordFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

        });
    }
    /**
     * 邮箱重置密码
     */
    private void resetPasswordByEmail(View view) {
        String email=editText.getText().toString();
        if(TextUtils.isEmpty(email)){
            editText.setError("邮箱不可以为空");
            return;
        }
        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/checkCode";
        Map map = new HashMap();
        map.put("code",editCode.getText().toString());
        map.put("email",email);
        OkHttpManager.postF(uri, map,new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String responseData = "";
                        Result<String> result = null;
                        try {
                            responseData = response.body().string();
                            System.out.println(responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        try {
                            result = objectMapper.readValue(responseData,new TypeReference<Result<String>>(){});
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        if (result.getCode() == 1){
                            //验证码验证正确就去修改密码
                            changePassword();
                        }else{
                            Toast.makeText(FindPasswordFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

        });


    }
}