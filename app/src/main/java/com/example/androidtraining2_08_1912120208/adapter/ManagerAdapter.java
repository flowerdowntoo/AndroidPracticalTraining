package com.example.androidtraining2_08_1912120208.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;
import com.example.androidtraining2_08_1912120208.datepicker.CustomDatePicker;
import com.example.androidtraining2_08_1912120208.datepicker.DateFormatUtils;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ManagerAdapter extends BaseMultiItemQuickAdapter<rentalDto, BaseViewHolder> {

    private long id;

    public ManagerAdapter(List<rentalDto> data) {
        super(data);
        // 绑定 layout 对应的 type
//         addItemType(1, R.layout.item_home1);
        addItemType(2, R.layout.item_manager);

    }


    @Override
    //设置新闻具体信息
    protected void convert(@NotNull BaseViewHolder baseViewHolder, rentalDto rentalDto) {
//        switch (baseViewHolder.getItemViewType()){
//            //item_home1
////            case 1:
////                baseViewHolder.setText(R.id.textView,newsBean.getNewsName());
////                baseViewHolder.setText(R.id.textView,newsBean.getNewsTypeName());
////                Glide.with(getContext()).load(NetUtils.BASE_URL+newsBean.getImg1())
////                        .into((ImageView)baseViewHolder.getView(R.id.imageView));
////                break;
//            //item_home2
//            case 2:
                System.out.println("我的管理");
       Button appointment = baseViewHolder.findView(R.id.appointment);
       //审核状态
       TextView textView=baseViewHolder.findView(R.id.Sid);
        appointment.setOnClickListener(view->{
            id = rentalDto.getId();
            System.out.println(id);
            new AlertDialog.Builder(getContext())
                    .setTitle("审核")
                    .setMessage("审核是否通过")
                    .setPositiveButton("通过", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            appointment.setVisibility(view.INVISIBLE);
                            textView.setText("已通过");

                            String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/rental/checkRental";


                            SharedPreferences sharedPreferences=((Activity)getContext()).getSharedPreferences("setting",MODE_PRIVATE);

                            String Account = sharedPreferences.getString("Account","");


                            Map map = new HashMap();
                            System.out.println(getId());
                            map.put("id",String.valueOf(getId()));
                            map.put("Examinestate","1");

                            OkHttpManager.postF(uri,map,new Callback() {
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
                                                System.out.println("审核成功");
                                            } else {
                                                Toast.makeText(ManagerAdapter.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }
                            });

                        }
                    })
                    .setNegativeButton("拒绝",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            appointment.setVisibility(view.INVISIBLE);
                            textView.setText("未通过");

                            String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/rental/checkRental";


                            SharedPreferences sharedPreferences=((Activity)getContext()).getSharedPreferences("setting",MODE_PRIVATE);

                            String Account = sharedPreferences.getString("Account","");


                            Map map = new HashMap();
                            System.out.println(getId());
                            map.put("id",String.valueOf(getId()));
                            map.put("Examinestate","0");

                            OkHttpManager.postF(uri,map,new Callback() {
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
                                                System.out.println("审核成功");
                                            } else {
                                                Toast.makeText(ManagerAdapter.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }
                            });


                        }
                    })
                    .show();
        });
        baseViewHolder.setText(R.id.brandName,rentalDto.getCar().getPlatenumber());
        baseViewHolder.setText(R.id.name,rentalDto.getUser().getName());
        baseViewHolder.setText(R.id.carKindName,rentalDto.getCar().getBrand());
        baseViewHolder.setText(R.id.startT,rentalDto.getStartday());
        baseViewHolder.setText(R.id.endT,rentalDto.getFinishday());
        baseViewHolder.setText(R.id.startTime,rentalDto.getStarttime());
        baseViewHolder.setText(R.id.endTime,rentalDto.getFinishtime());


//                break;
//        }
    }
    public long getId() {
        return id;
    }







}
