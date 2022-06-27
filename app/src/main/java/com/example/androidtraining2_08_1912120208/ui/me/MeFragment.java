package com.example.androidtraining2_08_1912120208.ui.me;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.bean.User;
import com.example.androidtraining2_08_1912120208.ui.me.user.LoginFragment;
import com.example.androidtraining2_08_1912120208.ui.me.user.RegisterFragment;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MeFragment extends Fragment {


    private boolean isLogin;
    private LinearLayout linearLayout_admin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me,container,false);
        CircleImageView circleImageView=root.findViewById(R.id.circleImageView);
        circleImageView.setOnClickListener(this::click);
        TextView textView=root.findViewById(R.id.textView16);
        textView.setOnClickListener(this::click);
        linearLayout_admin=root.findViewById(R.id.linearLayout_admin);

        LinearLayout linearLayoutAbout=root.findViewById(R.id.linearLayout_about);

        linearLayoutAbout.setOnClickListener(this::about);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("setting",MODE_PRIVATE);

        String Account = sharedPreferences.getString("Account","");
        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/user/getUserByAccount/"+Account;

        if (!Account.isEmpty()){

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
                                textView.setText(result.getData().getUsername());
                                if (result.getData().getIsadmin() == 1){ //1代表管理员 0是用户
                                    System.out.println(result.getData().getIsadmin());
                                    linearLayout_admin.setVisibility(View.VISIBLE);
                                }else{
                                    System.out.println(result.getData().getIsadmin());
                                    linearLayout_admin.setVisibility(View.GONE);
                                }
                                isLogin = true;
                            } else {
                                Toast.makeText(MeFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                                linearLayout_admin.setVisibility(View.GONE);
                                isLogin = false;
                            }
                        }
                    });
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }
            });
        }else{
            sharedPreferences.edit().remove("Account").apply();
            textView.setText("点击登录");
            linearLayout_admin.setVisibility(View.GONE);
            isLogin = false;

        }

        LinearLayout linearLayout_car=root.findViewById(R.id.linearLayout_car);
        linearLayout_car.setOnClickListener(view -> {
            if(Account.isEmpty()){
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }else{
                Navigation.findNavController(view).navigate(R.id.action_navigation_me_to_myCarFragment);
            }

        });

        LinearLayout linearLayout_map=root.findViewById(R.id.linearLayout_map);
        linearLayout_map.setOnClickListener(view -> Navigation.
                findNavController(view).navigate(R.id.action_navigation_me_to_mapFragment));
        //管理员审核
        LinearLayout linearLayout_admin=root.findViewById(R.id.linearLayout_admin);
        linearLayout_admin.setOnClickListener(view -> Navigation.
                findNavController(view).navigate(R.id.action_navigation_me_to_managerFragment));


        LinearLayout linearLayout_yu=root.findViewById(R.id.linearLayout_yu);
        linearLayout_yu.setOnClickListener(view -> {
            if(Account.isEmpty()){
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }else{
                System.out.println("跳转到预约页面");
               Navigation.findNavController(view).navigate(R.id.action_navigation_me_to_appointmentMyFragment);
            }

        });

        LinearLayout linearLayout_fa=root.findViewById(R.id.linearLayout_fa);
        linearLayout_fa.setOnClickListener(view -> {
            if(Account.isEmpty()){
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }else{
                System.out.println("跳转到发布页面");
                Navigation.findNavController(view).navigate(R.id.action_navigation_me_to_publishFragment);
            }

        });

        return root;
    }

    private void click(View view) {
        if(isLogin){
            Navigation.findNavController(view).navigate(R.id.action_navigation_me_to_infoFragment);
        }
        else{
            Navigation.findNavController(view).navigate(R.id.action_navigation_me_to_loginFragment);
        }
    }

    private void about(View view) {
        new AlertDialog.Builder(getContext())
                .setTitle("关于我们")
                .setMessage("欢迎使用Drive")
                .setPositiveButton("确定", null)
                .show();
    }
}