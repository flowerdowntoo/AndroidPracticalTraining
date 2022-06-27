package com.example.androidtraining2_08_1912120208.ui.me.user;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class addcarsFragment extends Fragment {
    private String image;
    private ImageView imageView;

    private EditText Platenumber;
    private EditText type;
    private EditText color;
    private Button confirm;
    private Button cencle;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_addcars, container, false);
        //头像上传
        imageView =root.findViewById(R.id.picture);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }

        });
        Platenumber = root.findViewById(R.id.cardId);
        type = root.findViewById(R.id.carKind);
        color = root.findViewById(R.id.color);
        confirm = root.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regClick2(v);
            }
        });

        return root ;
    }

    public void regClick2(View view){
        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/car/addCar";
        Map map=new HashMap();
        String Platenumber1 = Platenumber.getText().toString();
        String type1=type.getText().toString();
        String color1=color.getText().toString();

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("setting",MODE_PRIVATE);

        String account = sharedPreferences.getString("Account","");

        map.put("brand",type1);
        map.put("Platenumber",Platenumber1);
        map.put("color",color1);
        map.put("userid",account);


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
                            Toast.makeText(addcarsFragment.this.getContext(), "添加成功!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigateUp();
                        }else{
                            Toast.makeText(addcarsFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            Log.e(this.getClass().getName(), "Result:" + data.toString());
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                imageView.setImageURI(uri);
                image = uri.toString();
                Log.e(this.getClass().getName(), "Uri:" + String.valueOf(uri));
            }
        }
    }
}