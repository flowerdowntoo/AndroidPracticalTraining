package com.example.androidtraining2_08_1912120208.ui.me.user;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.adapter.AllAppointmentAdapter;
import com.example.androidtraining2_08_1912120208.bean.Car;
import com.example.androidtraining2_08_1912120208.bean.Rental;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class PublishNewFragment extends Fragment {
    
    private Button selectCar;
    private Button startDate;
    private TextView startDateText;
    private Button startTime;
    private TextView startTimeText;
    private Button endDate;
    private TextView endDateText;
    private Button endTime;
    private TextView endTimeText;
    private  Calendar calendar;
    private Button confirm;
    private TextView choose;
    private Rental rental = new Rental();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        calendar= Calendar.getInstance(Locale.CHINA);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_publish_new, container, false);
        //????????????
        selectCar = root.findViewById(R.id.selectCar);
        selectCar.setOnClickListener(this::chooseCar);
        //??????????????????
        startDateText=root.findViewById(R.id.selectStartDate);
        startDate = root.findViewById(R.id.startDate);
        startDate.setOnClickListener(this::onClick);
        //??????????????????
        startTime=root.findViewById(R.id.selectStartTime);
        startTimeText=root.findViewById(R.id.startTime);
        startTime.setOnClickListener(this::onClick);
        //??????????????????
        endDateText=root.findViewById(R.id.endDate);
        endDate = root.findViewById(R.id.selectEndDate);
        endDate.setOnClickListener(this::onClick);
        //??????????????????
        endTimeText=root.findViewById(R.id.endTime);
        endTime = root.findViewById(R.id.selectEndTime);
        endTime.setOnClickListener(this::onClick);
        //??????
        confirm=root.findViewById(R.id.confirm);
        confirm.setOnClickListener(this::save);

        //????????????
        choose = root.findViewById(R.id.choose);
        return root;
    }

    private void save(View view) {


        Toast.makeText(getContext(), "????????????", Toast.LENGTH_SHORT).show();

        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/rental/addRentalInfo";


        SharedPreferences sharedPreferences=((Activity)getContext()).getSharedPreferences("setting",MODE_PRIVATE);

        String Account = sharedPreferences.getString("Account","");


        Map map = new HashMap();
        System.out.println(getId());
        map.put("Userid",String.valueOf(Account));
        map.put("Carid",rental.getCarid());
        map.put("Startday",rental.getStartday());
        map.put("Finishday",rental.getFinishday());
        map.put("Starttime",rental.getStarttime());
        map.put("Finishtime",rental.getFinishtime());


        OkHttpManager.postF(uri,map,new Callback() {
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
                        System.out.println(result);
                        System.out.println(result.getData());
                        if (result.getCode() == 1) {
                            System.out.println("????????????");
                        } else {
                            Toast.makeText(PublishNewFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startDate:
                showDatePickerDialog(this.getActivity(),  0, startDateText, calendar);;
                break;
            case R.id.selectEndDate:
                showDatePickerDialog(this.getActivity(),  0, endDateText, calendar);;
                break;
            case R.id.selectStartTime:
                showTimePickerDialog(this.getActivity(),  2, startTimeText, calendar);
                break;
            case R.id.selectEndTime:
                showTimePickerDialog(this.getActivity(),  2, endTimeText, calendar);
                break;
            default:
                break;
        }
    }


    private void chooseCar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("???????????????");

        SharedPreferences sharedPreferences=((Activity)getContext()).getSharedPreferences("setting",MODE_PRIVATE);
        String Account = sharedPreferences.getString("Account","");

        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/car/getMyCarInfo/"+Account;

        final List<Car>cars = new ArrayList();
        final List<String>carstr= new ArrayList<String>();

        OkHttpManager.get(uri,new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String responseData = "";
                        Result<List<Car>> result = null;
                        try {

                            responseData = response.body().string();
                            System.out.println(responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        try {
                            result = objectMapper.readValue(responseData,new TypeReference<Result<List<Car>>>(){});
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        System.out.println(result);
                        System.out.println(result.getData());
                        if (result.getCode() == 1) {
                            for (int i = 0; i < result.getData().size(); i++) {
                                cars.add(result.getData().get(i));
                                carstr.add(result.getData().get(i).getBrand());
                                System.out.println(result.getData().get(i).getBrand());
                            }
                            final String[]cars2 = carstr.toArray(new String[carstr.size()]);
                            //    ????????????????????????????????????
                            builder.setItems(cars2, new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {

                                    System.out.println(cars.get(which).getId());
                                    choose.setText(cars2[which]);
                                    rental.setCarid(String.valueOf(cars.get(which).getId()));
                                    Toast.makeText(getActivity(), "?????????????????????" + cars2[which], Toast.LENGTH_SHORT).show();
                                }
                            });
                            AlertDialog r_dialog = builder.create();
                            r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            r_dialog.show();
                        } else {
                            Toast.makeText(PublishNewFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

    }
    /**
     * ????????????
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {


        // ??????????????????DatePickerDialog???????????????????????????????????????
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // ???????????????(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                if(tv == startDateText ){
                    rental.setStartday(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                } else{
                    rental.setFinishday(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                }

                // ????????????????????????????????????????????????????????????
                tv.setText(  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }
                // ??????????????????
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    /**
     * ????????????
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public  void showTimePickerDialog(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // ????????????TimePickerDialog??????????????????????????????
        // ???????????????Activity???context?????????

        new TimePickerDialog( activity,themeResId,
                // ???????????????

                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



                        String m = String.format(" %02d", minute);
                        String h = String.format(" %02d", hourOfDay);

                        if(tv == startTimeText ){
                            rental.setStarttime(h + ":" + m);
                        } else{
                            rental.setFinishtime(h + ":" + m);
                        }

                        tv.setText(h + ":" + m  );

                    }
                }
                // ??????????????????
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true????????????24?????????
                ,true).show();
    }

}