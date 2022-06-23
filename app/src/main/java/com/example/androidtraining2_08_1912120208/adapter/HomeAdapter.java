package com.example.androidtraining2_08_1912120208.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.NewsBean;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<rentalDto, BaseViewHolder> {

    public HomeAdapter(List<rentalDto> data) {
        super(data);
        // 绑定 layout 对应的 type
//         addItemType(1, R.layout.item_home1);
        addItemType(2, R.layout.item_home2);

    }

    @Override
    //设置新闻具体信息
    protected void convert(@NotNull BaseViewHolder baseViewHolder, rentalDto rentalDto) {
        switch (baseViewHolder.getItemViewType()){
            //item_home1
//            case 1:
//                baseViewHolder.setText(R.id.textView,newsBean.getNewsName());
//                baseViewHolder.setText(R.id.textView,newsBean.getNewsTypeName());
//                Glide.with(getContext()).load(NetUtils.BASE_URL+newsBean.getImg1())
//                        .into((ImageView)baseViewHolder.getView(R.id.imageView));
//                break;
            //item_home2
            case 2:
                baseViewHolder.setText(R.id.brandName,rentalDto.getCar().getPlatenumber());
                baseViewHolder.setText(R.id.name,rentalDto.getUser().getName());
                baseViewHolder.setText(R.id.carKindName,rentalDto.getCar().getBrand());
                baseViewHolder.setText(R.id.startT,rentalDto.getStarttime());
                baseViewHolder.setText(R.id.endT,rentalDto.getFinishtime());
                baseViewHolder.setText(R.id.startTime,rentalDto.getStartday());
                baseViewHolder.setText(R.id.endTime,rentalDto.getFinishday());
                break;
        }
    }
}
