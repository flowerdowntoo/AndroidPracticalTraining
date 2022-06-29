package com.example.androidtraining2_08_1912120208.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.bean.appointmentDto;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class PublishOldAdapter extends BaseMultiItemQuickAdapter<rentalDto, BaseViewHolder> {
    private long rentalId;
    private long id;
    private Button appointment;
    public PublishOldAdapter(List<rentalDto> data) {
        super(data);
        // 绑定 layout 对应的 type
//         addItemType(1, R.layout.item_home1);
        addItemType(2, R.layout.item_publishold);


    }

    @Override
    //设置新闻具体信息
    protected void convert(@NotNull BaseViewHolder baseViewHolder, rentalDto rentalDto) {


        if(rentalDto.getExaminestate() ==1){
            ((TextView)baseViewHolder.findView(R.id.Sid)).setText("已通过");
        }else if(rentalDto.getExaminestate() == -1){
            ((TextView)baseViewHolder.findView(R.id.Sid)).setText("未审核");
        }else{
            ((TextView)baseViewHolder.findView(R.id.Sid)).setText("未通过");
        }

        baseViewHolder.setText(R.id.brandName,rentalDto.getCar().getPlatenumber());
        baseViewHolder.setText(R.id.name,rentalDto.getUser().getName());
        baseViewHolder.setText(R.id.carKindName,rentalDto.getCar().getBrand());
        baseViewHolder.setText(R.id.startT,rentalDto.getStartday());
        baseViewHolder.setText(R.id.endT,rentalDto.getFinishday());
        baseViewHolder.setText(R.id.startTime,rentalDto.getStarttime());
        baseViewHolder.setText(R.id.endDate,rentalDto.getFinishtime());
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
