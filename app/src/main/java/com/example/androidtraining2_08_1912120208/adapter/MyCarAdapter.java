package com.example.androidtraining2_08_1912120208.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.Car;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyCarAdapter extends BaseMultiItemQuickAdapter<Car, BaseViewHolder> {

    public MyCarAdapter(List<Car> data) {
        super(data);
        // 绑定 layout 对应的 type
//         addItemType(1, R.layout.item_home1);
        addItemType(2, R.layout.item_mycar);


    }


    @Override
    //设置新闻具体信息
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Car car) {

        System.out.println("我的车辆");

                baseViewHolder.setText(R.id.cardId,car.getPlatenumber());
                baseViewHolder.setText(R.id.brand,car.getBrand());
                baseViewHolder.setText(R.id.color,car.getColor());
                baseViewHolder.findView(R.id.mycar_item).setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Holo_Light_Dialog);
                    //builder.setIcon(R.drawable.ic_launcher);

                    builder.setTitle("选择操作");
                    //    指定下拉列表的显示数据
                    final String[] c = {"详情","删除"};
                    //    设置一个下拉的列表选择项
                    builder.setItems(c, new DialogInterface.OnClickListener()
                    {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

//                Toast.makeText(getActivity(), "选择的操作为：" + c[which], Toast.LENGTH_SHORT).show();
                            if(c[which]=="详情"){
                                Bundle bundle=new Bundle();
                                bundle.putString("carId",String.valueOf(car.getId()));
                                Navigation.findNavController(view).navigate(R.id.action_myCarFragment_to_cardetilFragment,bundle);
                            }else{


                                String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/car/removeCar/"+car.getId();

                                OkHttpManager.get(uri,new Callback() {
                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        ((Activity)getContext()).runOnUiThread(new Runnable() {
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
                                                System.out.println(result);
                                                System.out.println(result.getData());
                                                if (result.getCode() == 1) {
                                                    removeAt(which);
                                                    System.out.println("删除成功");
                                                } else {
                                                    Toast.makeText(MyCarAdapter.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

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
                    });

                    AlertDialog r_dialog = builder.create();
                    r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    r_dialog.show();

                });
            }
}