package com.example.androidtraining2_08_1912120208.ui.me.user;

import android.media.session.MediaSession;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.base.BaseFragment2;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.bean.User;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterFragment extends BaseFragment2 {

    private EditText name;
    private EditText password;
    private EditText password2;
    private EditText userName;
    private EditText account;
    private EditText phone;
    private EditText email;
    private RadioGroup radioGroup;
    private Button register;
    private RadioButton sex;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_register, container, false);
        password=root.findViewById(R.id.password);
        password2=root.findViewById(R.id.password2);
        name=root.findViewById(R.id.name);
        userName=root.findViewById(R.id.username);
        account=root.findViewById(R.id.account);
//        email=root.findViewById(R.id.email);
        phone=root.findViewById(R.id.phone);


         radioGroup = root.findViewById(R.id.sex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex =  root.findViewById(radioGroup.getCheckedRadioButtonId());
            }
        });
        register = root.findViewById(R.id.button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regClick2(v);
            }
        });
        return root;
    }


    //    /**
//     * 账号密码注册
//     */
//    public void signUp(final View view) {
//
//
//        String sex1 = "男";
//
//        if(TextUtils.isEmpty(account1)){
//            account.setError("账号不可以为空");
//            return;
//        }
//        if(TextUtils.isEmpty(userName1)){
//            account.setError("密码不可以为空");
//            return;
//        }
//        if(TextUtils.isEmpty(userName1)){
//            account.setError("密码不可以为空");
//            return;
//        }
//        if(TextUtils.isEmpty(name1)){
//            account.setError("姓名不可以为空");
//            return;
//        }
//        if(TextUtils.isEmpty(userName1)){
//            account.setError("昵称不可以为空");
//            return;
//        }
//        final User user = new User();
//        user.setSex(sex1);
//        user.setPassword(password1);
//        user.setAccount(name1);
//        user.setUserName(userName1);
//        user.setName(name1);
//        UserDao.add(user);
//
//    }
    public void regClick2(View view){
        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/user/register";
        Map map=new HashMap();
        String name1 = name.getText().toString();
        String userName1=userName.getText().toString();
        String password1 = password.getText().toString();
        String password21 = password2.getText().toString();
        String account1=account.getText().toString();
        String sex1 = sex.getText().toString();
//        String email1 = email.getText().toString();
        String phone1 = phone.getText().toString();

        map.put("account",account1);
        map.put("password",password1);
        map.put("name",name1);
        map.put("username",userName1);
        map.put("sex",sex1);
        map.put("number",phone1);
//        map.put("email",email1);


        OkHttpManager.postF(uri, map, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        String responseData = "";
                        try {
                            responseData = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Result<String> result = gson.fromJson(responseData,Result.class);
                        if (result.getCode() == 1){
                            Toast.makeText(RegisterFragment.this.getContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigateUp();
                        }else{
                            Toast.makeText(RegisterFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
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