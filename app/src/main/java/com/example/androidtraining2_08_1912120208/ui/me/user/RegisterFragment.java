package com.example.androidtraining2_08_1912120208.ui.me.user;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
    private String image;
    private de.hdodenhof.circleimageview.CircleImageView CircleImageView;
    private Bitmap bitmap;
    private ImageView img;
    private String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_register, container, false);
        password=root.findViewById(R.id.password);
        password2=root.findViewById(R.id.password2);
        name=root.findViewById(R.id.name);
        userName=root.findViewById(R.id.username);
        account=root.findViewById(R.id.account);
        email=root.findViewById(R.id.email);
        phone=root.findViewById(R.id.phone);

        //????????????
        CircleImageView =root.findViewById(R.id.circleImageView);

        CircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });



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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // ????????????????????????
            ContentResolver cr = this.getActivity().getContentResolver();
            Uri uri = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                CircleImageView.setImageBitmap(bitmap);
                Cursor cursor = cr.query(uri,new String[]{MediaStore.Images.Media.DATA},null,null,null);

                if (cursor.moveToFirst()){
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    System.out.println(path);
                    upload();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void upload(){
        File file = new File(path);
        try {
            String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/common/upload";
            System.out.println(uri);
            System.out.println(path);
            URL url = new URL(uri);
            OkHttpManager.postFile(url, file, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    System.out.println("??????");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterFragment.this.getContext(),"??????", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    //    /**
//     * ??????????????????
//     */
//    public void signUp(final View view) {
//
//
//        String sex1 = "???";
//
//        if(TextUtils.isEmpty(account1)){
//            account.setError("?????????????????????");
//            return;
//        }
//        if(TextUtils.isEmpty(userName1)){
//            account.setError("?????????????????????");
//            return;
//        }
//        if(TextUtils.isEmpty(userName1)){
//            account.setError("?????????????????????");
//            return;
//        }
//        if(TextUtils.isEmpty(name1)){
//            account.setError("?????????????????????");
//            return;
//        }
//        if(TextUtils.isEmpty(userName1)){
//            account.setError("?????????????????????");
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
        String email1 = email.getText().toString();
        String phone1 = phone.getText().toString();


        map.put("account",account1);
        map.put("password",password1);
        map.put("name",name1);
        map.put("username",userName1);
        map.put("sex",sex1);
        map.put("number",phone1);
        map.put("image",image);
        map.put("email",email1);


        OkHttpManager.postF(uri, map, new Callback() {
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
                            Toast.makeText(RegisterFragment.this.getContext(), "????????????!", Toast.LENGTH_SHORT).show();
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