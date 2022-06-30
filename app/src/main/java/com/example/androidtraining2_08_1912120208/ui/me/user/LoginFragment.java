package com.example.androidtraining2_08_1912120208.ui.me.user;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.mapapi.common.SysOSUtil;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.R;

import com.example.androidtraining2_08_1912120208.base.BaseFragment2;
import com.example.androidtraining2_08_1912120208.bean.User;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginFragment extends BaseFragment2 {

    private EditText account;
    private EditText password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root=inflater.inflate(R.layout.fragment_login, container, false);
        account=root.findViewById(R.id.account);
        password=root.findViewById(R.id.password);
        TextView textView=root.findViewById(R.id.register);
        textView.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });
        TextView textView2=root.findViewById(R.id.findPassword);
        textView2.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.findPasswordFragment);
        });
//        Button button=root.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginClick(v);
//            }
//        });

        return root;


    }


    public void loginClick(View view){
        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/user/login";
        Map map=new HashMap();

        String account1 = account.getText().toString();
        String password1=password.getText().toString();

        if(TextUtils.isEmpty(account1)){
            account.setError("账号不可以为空");
            return;
        }
        if(TextUtils.isEmpty(password1)){
            password.setError("密码不可以为空");
            return;
        }


        map.put("account",account1);
        map.put("password",password1);


        OkHttpManager.postF(uri, map, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String responseData = "";
                                Result<User> result = null;
                                try {
                                    responseData = response.body().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ObjectMapper objectMapper = new ObjectMapper();
                                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                                try {
                                    result = objectMapper.readValue(responseData,new TypeReference<Result<User>>(){});
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }


                                if (result.getCode() == 1) {
                                    Toast.makeText(LoginFragment.this.getContext(), "登录成功!", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("setting",MODE_PRIVATE);

                                    sharedPreferences.edit().putString("Account",result.getData().getAccount()).apply();

                                    Navigation.findNavController(view).navigateUp();
                                } else {
                                    Toast.makeText(LoginFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
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