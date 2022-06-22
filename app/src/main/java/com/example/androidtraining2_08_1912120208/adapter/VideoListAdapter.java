package com.example.androidtraining2_08_1912120208.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidtraining2_08_1912120208.R;

import java.util.List;

public class VideoListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public VideoListAdapter(@Nullable List<String> data) {
        super(R.layout.item_video_list,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
       baseViewHolder.setText(R.id.textView16,s);

    }
}
