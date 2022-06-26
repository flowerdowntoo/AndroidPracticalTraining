package com.example.androidtraining2_08_1912120208.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;
import com.example.androidtraining2_08_1912120208.datepicker.CustomDatePicker;
import com.example.androidtraining2_08_1912120208.datepicker.DateFormatUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                            Toast.makeText(getContext(), "通过", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("拒绝",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            appointment.setVisibility(view.INVISIBLE);
                            textView.setText("未通过");
                            Toast.makeText(getContext(), "未通过", Toast.LENGTH_SHORT).show();
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
