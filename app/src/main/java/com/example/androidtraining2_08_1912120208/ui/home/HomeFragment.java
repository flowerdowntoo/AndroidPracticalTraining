package com.example.androidtraining2_08_1912120208.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.adapter.HomeAdapter;
import com.example.androidtraining2_08_1912120208.adapter.ImageAdapter;
import com.example.androidtraining2_08_1912120208.adapter.ImageTitleNumAdapter;
import com.example.androidtraining2_08_1912120208.bean.NewsBean;
import com.example.androidtraining2_08_1912120208.databinding.FragmentHomeBinding;
import com.example.androidtraining2_08_1912120208.ui.chart.ChartViewModel;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private  HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private Banner<?, BannerAdapter<?,?>> banner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        View root = inflater.inflate(R.layout.fragment_home,container,false);

        //导入刷新模板
        RefreshLayout refreshLayout = (RefreshLayout)root.findViewById(R.id.refreshLayout);
        RecyclerView recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //创建首页适配器
         homeAdapter = new HomeAdapter(null);
        recyclerView.setAdapter(homeAdapter);
            //加入空白view
            homeAdapter.setEmptyView(R.layout.empty_home);


            getAdList();//测试注释
            getNewsList();

        //创建头部view，并添加
        View headerView=inflater.inflate(R.layout.header_home,container,false);
        homeAdapter.addHeaderView(headerView);
        homeAdapter.setHeaderWithEmptyEnable(true);
        //--------------------------简单使用轮播图-------------------------------
       banner= headerView.findViewById(R.id.banner);
       //启动轮播图
        banner.addBannerLifecycleObserver(this)
                .setPageTransformer(new ZoomOutPageTransformer())
                .start();//添加生命周期观察者
             // .setIndicator(new RoundLinesIndicator(getContext()))//指示器
        //图片列表
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            list.add(R.drawable.pic_item_list_default);
        }

        banner.setAdapter(new ImageAdapter(list));
        refreshLayout.setOnRefreshListener(refresh -> {
            refresh.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            //得到新闻列表
            getAdList();
            getNewsList();
        });
        refreshLayout.setOnLoadMoreListener(refresh -> {
            refresh.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            Toast.makeText(getActivity(),"没有更多数据",Toast.LENGTH_SHORT).show();
        });
        //从主页跳转python页面 python详情页
//        LinearLayout linearLayout_python = headerView.findViewById(R.id.linearLayout_python);
//        linearLayout_python.setOnClickListener(v-> Navigation.findNavController(v)
//                .navigate(R.id.action_navigation_home_to_pythonFragment));

        //传入新闻网站
        homeAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle=new Bundle();
            bundle.putString("url",homeAdapter.getData().get(position).getNewsUrl());
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_webFragment,
                    bundle);
        });



        return root;
    }
    //得到广告列表
    private void getAdList() {
        homeViewModel.getAdList().observe(getViewLifecycleOwner(), newsBeans -> {
//            for(NewsBean newsBean:newsBeans){
//                Log.i("Ad",newsBean.getNewsName());
//            }
            //设置轮播图适配器
            banner.setAdapter(new ImageTitleNumAdapter(newsBeans));
            banner.setOnBannerListener((data, position) -> {
                Bundle bundle=new Bundle();
                bundle.putString("url",((NewsBean)data).getNewsUrl());
                Navigation.findNavController(banner).navigate(R.id.action_navigation_home_to_webFragment,
                        bundle);

            });

        });
    }
    //得到新闻列表
    private void getNewsList() {
        homeViewModel.getNewsList().observe(getViewLifecycleOwner(), newsBeans -> {
//            for(NewsBean newsBean:newsBeans){
//                Log.i("News",newsBean.getNewsName());
//            }
            homeAdapter.setList(newsBeans);
        });
    }


}