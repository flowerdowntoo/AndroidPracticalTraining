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
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.bean.User;
import com.example.androidtraining2_08_1912120208.bean.rentalDto;

import com.example.androidtraining2_08_1912120208.ui.me.user.InfoFragment;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private  HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private Banner<?, BannerAdapter<?,?>> banner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        View root = inflater.inflate(R.layout.fragment_home,container,false);

        //??????????????????
        RefreshLayout refreshLayout = (RefreshLayout)root.findViewById(R.id.refreshLayout);
        RecyclerView recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //?????????????????????
         homeAdapter = new HomeAdapter(null);
        recyclerView.setAdapter(homeAdapter);
            //????????????view
            homeAdapter.setEmptyView(R.layout.empty_home);


            getAdList();//????????????
            getNewsList();

        //????????????view????????????
        View headerView=inflater.inflate(R.layout.header_home,container,false);
        homeAdapter.addHeaderView(headerView);
        homeAdapter.setHeaderWithEmptyEnable(true);

        //--------------------------?????????????????????-------------------------------
       banner= headerView.findViewById(R.id.banner);
       //???????????????
        banner.addBannerLifecycleObserver(this)
                .setPageTransformer(new ZoomOutPageTransformer())
                .start();//???????????????????????????
             // .setIndicator(new RoundLinesIndicator(getContext()))//?????????
        //????????????
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            list.add(R.drawable.pic_item_list_default);
        }

        banner.setAdapter(new ImageAdapter(list));
        refreshLayout.setOnRefreshListener(refresh -> {
            refresh.finishRefresh(2000/*,false*/);//??????false??????????????????
            //??????????????????
            getAdList();
            getNewsList();
        });
        refreshLayout.setOnLoadMoreListener(refresh -> {
            refresh.finishLoadMore(2000/*,false*/);//??????false??????????????????
            Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
        });
        //???????????????python?????? python?????????
//        LinearLayout linearLayout_python = headerView.findViewById(R.id.linearLayout_python);
//        linearLayout_python.setOnClickListener(v-> Navigation.findNavController(v)
//                .navigate(R.id.action_navigation_home_to_pythonFragment));




        return root;
    }


    //??????????????????
    private void getAdList() {
        homeViewModel.getAdList().observe(getViewLifecycleOwner(), newsBeans -> {
//            for(NewsBean newsBean:newsBeans){
//                Log.i("Ad",newsBean.getNewsName());
//            }
            //????????????????????????
            banner.setAdapter(new ImageTitleNumAdapter(newsBeans));
            banner.setOnBannerListener((data, position) -> {
                Bundle bundle=new Bundle();
                bundle.putString("url",((NewsBean)data).getNewsUrl());
                Navigation.findNavController(banner).navigate(R.id.action_navigation_home_to_webFragment,
                        bundle);

            });

        });
    }
    //??????????????????
    private void getNewsList() {

        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/rental/getRentalInfo";

        OkHttpManager.get(uri, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String responseData = "";
                        Result<List<rentalDto>> result = null;
                        try {

                            responseData = response.body().string();
                            System.out.println(responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        try {
                            result = objectMapper.readValue(responseData,new TypeReference<Result<List<rentalDto>>>(){});
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        System.out.println(result);
                        System.out.println(result.getData());
                        if (result.getCode() == 1) {
                           homeAdapter.setList(result.getData());

                        } else {
                            Toast.makeText(HomeFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });


    }


}