package com.example.androidtraining2_08_1912120208.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.Car;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                    System.out.println("我的车辆");
        //       LinearLayout linearLayout_car = root.findViewById(R.id.mycar_item);
//        linearLayout_car.setOnClickListener(this::click);
                baseViewHolder.setText(R.id.cardId,car.getPlatenumber());
                baseViewHolder.setText(R.id.brand,car.getBrand());
                baseViewHolder.setText(R.id.color,car.getColor());
            baseViewHolder.findView(R.id.mycar_item).setOnClickListener(this::click);
//                baseViewHolder.setText(R.id.startTime,rentalDto.getStarttime());
//                baseViewHolder.setText(R.id.endTime,rentalDto.getFinishtime());

//                break;
//        }

    }

    public void click(View view) {
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

                    Navigation.findNavController(view).navigate(R.id.action_myCarFragment_to_carMessage);
//                    Toast.makeText(getContext(), "跳转", Toast.LENGTH_SHORT).show();
                }

            }
        });

        AlertDialog r_dialog = builder.create();
        r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        r_dialog.show();


    }




}
