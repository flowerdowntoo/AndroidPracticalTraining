package com.example.androidtraining2_08_1912120208.ui.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.adapter.VideoAdapter;
import com.example.androidtraining2_08_1912120208.bean.VideoBean;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends Fragment {

    private  VideoViewModel videoViewModel;
    private VideoAdapter videoAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       videoViewModel =
                new ViewModelProvider(this).get(VideoViewModel.class);

        View root = inflater.inflate(R.layout.fragment_video,container,false);
        //导入刷新模板
        RefreshLayout refreshLayout = (RefreshLayout)root.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                getVideoList();
            }


        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            Toast.makeText(getActivity(),"没有更多数据",Toast.LENGTH_SHORT).show();
        });
        getVideoList();
        RecyclerView recyclerView=root.findViewById(R.id.recyclerView);
        videoAdapter=new VideoAdapter(null);
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        videoAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle=new Bundle();
            VideoBean videoBean=videoAdapter.getData().get(position);
            bundle.putString("image",videoBean.getImg());
            bundle.putString("name",videoBean.getName());
            bundle.putString("intro",videoBean.getIntro());
            List<String> list=new ArrayList<>();
            for (VideoBean.VideoDetailListBean videoDetailListBean:
                videoBean.getVideoDetailList() ) {
                list.add(videoDetailListBean.getVideo_name());
            }
            bundle.putStringArray("list",list.toArray(new String[0]));
            Navigation.findNavController(root).navigate
                    (R.id.action_navigation_video_to_videoDetailFragment,bundle);
        });
        return root;
    }
    //得到视频列表
    private void getVideoList() {
        videoViewModel.getVideoList().observe(getViewLifecycleOwner(), videoBeans -> {
//            for(VideoBean videoBean:videoBeans){
//                Log.i("Video",videoBean.getName());
//            }
            videoAdapter.setList(videoBeans);

        });
    }




}