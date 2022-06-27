package com.example.androidtraining2_08_1912120208.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AppointEndAdapter extends BaseMultiItemQuickAdapter<rentalDto, BaseViewHolder> {
    private long rentalId;
    private long id;
    public AppointEndAdapter(List<rentalDto> data) {
        super(data);
        // 绑定 layout 对应的 type
//         addItemType(1, R.layout.item_home1);
        addItemType(2, R.layout.item_appointment_end);


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
        //       LinearLayout linearLayout_car = root.findViewById(R.id.mycar_item);
//        linearLayout_car.setOnClickListener(this::click);
        baseViewHolder.setText(R.id.brandName,rentalDto.getCar().getPlatenumber());
        baseViewHolder.setText(R.id.name,rentalDto.getUser().getName());
        baseViewHolder.setText(R.id.carKindName,rentalDto.getCar().getBrand());
        baseViewHolder.setText(R.id.startT,rentalDto.getStartday());
        baseViewHolder.setText(R.id.endT,rentalDto.getFinishday());
        baseViewHolder.setText(R.id.startTime,rentalDto.getStarttime());
        baseViewHolder.setText(R.id.endTime,rentalDto.getFinishtime());






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
