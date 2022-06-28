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
import com.example.androidtraining2_08_1912120208.bean.appointmentDto;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;
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

public class AppointMiddleAdapter extends BaseMultiItemQuickAdapter<appointmentDto, BaseViewHolder> {
    private long rentalId;
    private long id;
    public AppointMiddleAdapter(List<appointmentDto> data) {
        super(data);
        // 绑定 layout 对应的 type
//         addItemType(1, R.layout.item_home1);
        addItemType(2, R.layout.item_allappointment);


    }

    @Override
    //设置新闻具体信息
    protected void convert(@NotNull BaseViewHolder baseViewHolder, appointmentDto appointmentDto) {
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
        System.out.println("预约中");

        Button appointment = baseViewHolder.findView(R.id.cancel);
        appointment.setVisibility(View.VISIBLE);
        //审核状态
        TextView textView=baseViewHolder.findView(R.id.Sid);
        appointment.setOnClickListener(view->{
            rentalId = appointmentDto.getRental().getId();
            System.out.println(rentalId);
            new AlertDialog.Builder(getContext())
                    .setTitle("取消预约")
                    .setMessage("是否取消预约")
                    .setPositiveButton("返回", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            // appointment.setVisibility(view.INVISIBLE);
                            // textView.setText("已结束");
                            //   Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            appointment.setVisibility(view.INVISIBLE);
                            textView.setText("已结束");
                            Toast.makeText(getContext(), "已取消预约", Toast.LENGTH_SHORT).show();


                            String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/appointment/updateOrderState";


                            SharedPreferences sharedPreferences=((Activity)getContext()).getSharedPreferences("setting",MODE_PRIVATE);

                            String Account = sharedPreferences.getString("Account","");


                            Map map = new HashMap();
                            System.out.println(getId());
                            map.put("rentalid",String.valueOf(getId()));

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
                                                System.out.println("取消预约成功");
                                            } else {
                                                Toast.makeText(AppointMiddleAdapter.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

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
        //       LinearLayout linearLayout_car = root.findViewById(R.id.mycar_item);
//        linearLayout_car.setOnClickListener(this::click);
        baseViewHolder.setText(R.id.brandName, appointmentDto.getCar().getPlatenumber());
        baseViewHolder.setText(R.id.name, appointmentDto.getUser().getName());
        baseViewHolder.setText(R.id.carKindName, appointmentDto.getCar().getBrand());
        baseViewHolder.setText(R.id.startT, appointmentDto.getRental().getStartday());
        baseViewHolder.setText(R.id.endT, appointmentDto.getRental().getFinishday());
        baseViewHolder.setText(R.id.startTime, appointmentDto.getRental().getStarttime());
        baseViewHolder.setText(R.id.endTime, appointmentDto.getRental().getFinishtime());
        baseViewHolder.setText(R.id.endTime, appointmentDto.getRental().getFinishtime());
        baseViewHolder.setText(R.id.appointmentDate, appointmentDto.getAppointday());
        baseViewHolder.setText(R.id.appointmentTime, appointmentDto.getAppointtime());



//                baseViewHolder.setText(R.id.startTime,rentalDto.getStarttime());
//                baseViewHolder.setText(R.id.endTime,rentalDto.getFinishtime());

//                break;
//        }

    }
    @Override
    protected void setOnItemChildClick(@NonNull View v, int position) {
        System.out.println(position);
        super.setOnItemChildClick(v, position);
    }

    private void click(View view) {
//        Toast.makeText(getContext(), "2123", Toast.LENGTH_SHORT).show();
        // 日期格式为yyyy-MM-dd HH:mm


    }

    public long getId() {
        return rentalId;
    }
}
