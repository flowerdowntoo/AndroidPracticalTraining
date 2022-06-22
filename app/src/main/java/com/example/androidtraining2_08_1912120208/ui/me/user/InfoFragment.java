package com.example.androidtraining2_08_1912120208.ui.me.user;
import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.base.BaseFragment2;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.bean.User;
import com.example.androidtraining2_08_1912120208.ui.me.MeFragment;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import cn.bmob.v3.BmobUser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InfoFragment extends BaseFragment2 {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root= inflater.inflate(R.layout.fragment_info, container, false);
        TextView name=root.findViewById(R.id.name);
        TextView account=root.findViewById(R.id.account);
        TextView username=root.findViewById(R.id.username);
        TextView sex=root.findViewById(R.id.sex);
        TextView email=root.findViewById(R.id.email);
        TextView phone=root.findViewById(R.id.phone);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("setting",MODE_PRIVATE);
        String Account = sharedPreferences.getString("Account","");
        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/user/getUserByAccount/"+Account;

        OkHttpManager.get(uri, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String responseData = "";
                        Result<User> result = null;
                        try {
                            responseData = response.body().string();
                            System.out.println(responseData);
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
                        System.out.println(result);
                        System.out.println(result.getData());
                        if (result.getCode() == 1) {
                            System.out.println(result.getData().getUsername());
                            name.setText(result.getData().getName());
                            username.setText(result.getData().getUsername());
                            phone.setText(String.valueOf(result.getData().getNumber()));
                            sex.setText(result.getData().getSex());
                            account.setText(result.getData().getAccount());

                        } else {
                            Toast.makeText(InfoFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });


        Button button=root.findViewById(R.id.button);
        button.setOnClickListener(this::logout);
        return  root;
    }
    private void logout(View view) {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("setting",MODE_PRIVATE);
        String Account = sharedPreferences.getString("Account","");
        if (!Account.isEmpty()){
            sharedPreferences.edit().remove("Account").apply();
        }
        Navigation.findNavController(view).navigateUp();
        Snackbar.make(view, "退出登录", Snackbar.LENGTH_LONG).show();
    }
}