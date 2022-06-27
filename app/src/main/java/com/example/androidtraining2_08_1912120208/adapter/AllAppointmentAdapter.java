package com.example.androidtraining2_08_1912120208.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.Car;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllAppointmentAdapter extends BaseMultiItemQuickAdapter<rentalDto, BaseViewHolder>{
    private long rentalId;
    private long id;
    public AllAppointmentAdapter(List<rentalDto> data) {
        super(data);
        // 绑定 layout 对应的 type
//         addItemType(1, R.layout.item_home1);
        addItemType(2, R.layout.item_allappointment);


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
        System.out.println("我的预约");
            Button appointment = baseViewHolder.findView(R.id.cancel);
            //审核状态
            TextView textView=baseViewHolder.findView(R.id.Sid);
            appointment.setOnClickListener(view->{
                id = rentalDto.getId();
                System.out.println(id);
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
                            }
                        })
                        .show();
            });
        //       LinearLayout linearLayout_car = root.findViewById(R.id.mycar_item);
//        linearLayout_car.setOnClickListener(this::click);
            baseViewHolder.setText(R.id.brandName,rentalDto.getCar().getPlatenumber());
            baseViewHolder.setText(R.id.name,rentalDto.getUser().getName());
            baseViewHolder.setText(R.id.carKindName,rentalDto.getCar().getBrand());
            baseViewHolder.setText(R.id.startT,rentalDto.getStartday());
            baseViewHolder.setText(R.id.endT,rentalDto.getFinishday());
            baseViewHolder.setText(R.id.startTime,rentalDto.getStarttime());
            baseViewHolder.setText(R.id.endTime,rentalDto.getFinishtime());
            Button  button = baseViewHolder.findView(R.id.cancel);
            button.setVisibility(View.VISIBLE);





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
