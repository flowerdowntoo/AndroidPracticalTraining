package com.example.androidtraining2_08_1912120208.ui.me.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.androidtraining2_08_1912120208.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;


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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        calendar= Calendar.getInstance(Locale.CHINA);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_publish_new, container, false);
        //选择车辆
        selectCar = root.findViewById(R.id.selectCar);
        selectCar.setOnClickListener(this::chooseCar);
        //选择起始日期
        startDateText=root.findViewById(R.id.selectStartDate);
        startDate = root.findViewById(R.id.startDate);
        startDate.setOnClickListener(this::onClick);
        //选择起始时间
        startTime=root.findViewById(R.id.selectStartTime);
        startTimeText=root.findViewById(R.id.startTime);
        startTime.setOnClickListener(this::onClick);
        //选择结束日期
        endDateText=root.findViewById(R.id.endDate);
        endDate = root.findViewById(R.id.selectEndDate);
        endDate.setOnClickListener(this::onClick);
        //选择结束时间
        endTimeText=root.findViewById(R.id.endTime);
        endTime = root.findViewById(R.id.selectEndTime);
        endTime.setOnClickListener(this::onClick);
        //保存
        confirm=root.findViewById(R.id.confirm);
        confirm.setOnClickListener(this::save);

        return root;
    }

    private void save(View view) {
        Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
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
        builder.setTitle("选择一辆车");
        //    指定下拉列表的显示数据
        final String[] cars = {"奥迪", "宝马", "大众", "劳斯莱斯", "奔驰","奥迪", "宝马", "大众", "劳斯莱斯", "奔驰","奥迪", "宝马", "大众", "劳斯莱斯", "奔驰","奥迪", "宝马", "大众", "劳斯莱斯", "奔驰","奥迪", "宝马", "大众", "劳斯莱斯", "奔驰"};
        //    设置一个下拉的列表选择项
        builder.setItems(cars, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getActivity(), "选择的汽车为：" + cars[which], Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog r_dialog = builder.create();
        r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        r_dialog.show();

    }
    /**
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText(  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类


        new TimePickerDialog( activity,themeResId,
                // 绑定监听器

                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        String m = String.format(" %02d", minute);
                        String h = String.format(" %02d", hourOfDay);


                        tv.setText(h + ":" + m  );

                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }

}