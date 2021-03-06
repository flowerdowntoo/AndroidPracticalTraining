package com.example.androidtraining2_08_1912120208.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.activity.MainActivity;
import com.example.androidtraining2_08_1912120208.bean.NewsBean;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;
import com.example.androidtraining2_08_1912120208.datepicker.CustomDatePicker;
import com.example.androidtraining2_08_1912120208.datepicker.DateFormatUtils;
import com.example.androidtraining2_08_1912120208.ui.me.user.RegisterFragment;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeAdapter extends BaseMultiItemQuickAdapter<rentalDto, BaseViewHolder> {
    private TextView mTvSelectedDate, mTvSelectedTime;
    private Button appointment;
    private CustomDatePicker mDatePicker, mTimerPicker;
    private long rentalId;



    public HomeAdapter(List<rentalDto> data) {
        super(data);
        // ?????? layout ????????? type
//         addItemType(1, R.layout.item_home1);
        addItemType(2, R.layout.item_home2);

    }


    @Override
    //????????????????????????
    protected void convert(@NotNull BaseViewHolder baseViewHolder, rentalDto rentalDto) {


                appointment = baseViewHolder.findView(R.id.appointment);
                appointment.setOnClickListener(view->{
                    SharedPreferences sharedPreferences=((Activity)getContext()).getSharedPreferences("setting",MODE_PRIVATE);
                    String Account = sharedPreferences.getString("Account","");
                    if(Account.isEmpty()){
                      Navigation.findNavController(view).navigate(R.id.loginFragment);
                    }else{
                        initTimerPicker(rentalDto);
                        mTimerPicker.show(appointment.getText().toString());
                        rentalId = rentalDto.getId();
                    }
                });
                baseViewHolder.setText(R.id.brandName,rentalDto.getCar().getPlatenumber());
                baseViewHolder.setText(R.id.name,rentalDto.getUser().getName());
                baseViewHolder.setText(R.id.carKindName,rentalDto.getCar().getBrand());
                baseViewHolder.setText(R.id.startTime,rentalDto.getStartday());
                baseViewHolder.setText(R.id.endTime,rentalDto.getFinishday());
                baseViewHolder.setText(R.id.startT,rentalDto.getStarttime());
                baseViewHolder.setText(R.id.endT,rentalDto.getFinishtime());
    }



    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    @Override
    protected void setOnItemChildClick(@NonNull View v, int position) {
        System.out.println(position);
        super.setOnItemChildClick(v, position);
    }

    private void click(View view) {
//        Toast.makeText(getContext(), "2123", Toast.LENGTH_SHORT).show();
                // ???????????????yyyy-MM-dd HH:mm


    }

    public long getId() {
        return rentalId;
    }


    private void initTimerPicker(rentalDto rentalDto) {
        //????????????
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date2=new Date();
        String beginTime =sdf.format(date2);
//        DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        //????????????
        String endTime = rentalDto.getFinishday()+" "+rentalDto.getFinishtime();
        // ??????????????????????????????????????????????????????yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this.getContext(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {

                String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/appointment/makeAnAppointment";

                SharedPreferences sharedPreferences=((Activity)getContext()).getSharedPreferences("setting",MODE_PRIVATE);

                String Account = sharedPreferences.getString("Account","");

                String date  = DateFormatUtils.long2Str(timestamp, true);
                System.out.println(DateFormatUtils.long2Str(timestamp, true));


                Map map = new HashMap();
                map.put("rentalid",String.valueOf(getId()));
                map.put("telephone","17605093090");
                map.put("appointtime",date.substring(11,16));
                map.put("employerid",String.valueOf(Account));
                map.put("appointday",date.substring(0,10));
                OkHttpManager.postF(uri, map, new Callback() {
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
                                if (result.getCode() == 1){
                                    Navigation.findNavController(getRecyclerView()).navigate(R.id.navigation_home);
                                    Toast.makeText(getContext(), "????????????!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                });

            }
        }, beginTime, endTime);
        // ??????????????????????????????????????????
        mTimerPicker.setCancelable(true);
        // ???????????????
        mTimerPicker.setCanShowPreciseTime(true);
        // ??????????????????
        mTimerPicker.setScrollLoop(true);
        // ??????????????????
        mTimerPicker.setCanShowAnim(true);
    }



}
