package com.example.androidtraining2_08_1912120208.ui.me.user;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.adapter.AllAppointmentAdapter;
import com.example.androidtraining2_08_1912120208.adapter.PublishNewOrderAdapter;
import com.example.androidtraining2_08_1912120208.bean.Result;
import com.example.androidtraining2_08_1912120208.bean.appointmentDto;
import com.example.androidtraining2_08_1912120208.utils.NetUtils;
import com.example.androidtraining2_08_1912120208.utils.OkHttpManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class PublishNewOrderFragment extends Fragment {

    private PublishNewOrderAdapter publishNewOrderAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_publish_neworder, container, false);
        //导入刷新模板
        RefreshLayout refreshLayout = (RefreshLayout)root.findViewById(R.id.refreshLayout);
        //获取recyclerView
        RecyclerView recyclerView=root.findViewById(R.id.newOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        publishNewOrderAdapter=new PublishNewOrderAdapter(null);
        recyclerView.setAdapter(publishNewOrderAdapter);
        getNewsList();
        refreshLayout.setOnRefreshListener(refresh -> {
            refresh.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            //得到新闻列表
            getNewsList();
        });
        refreshLayout.setOnLoadMoreListener(refresh -> {
            refresh.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            Toast.makeText(getActivity(),"没有更多数据",Toast.LENGTH_SHORT).show();
        });



//       LinearLayout linearLayout_car = root.findViewById(R.id.mycar_item);
//        linearLayout_car.setOnClickListener(this::click);

        return root;

    }
    //得到新闻列表
    private void getNewsList() {

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("setting",MODE_PRIVATE);

        String account = sharedPreferences.getString("Account","");

        String uri= NetUtils.INTERNET_THROUGH_URL+"androidtest/appointment/getMyAppointmentInfo/"+account;

        OkHttpManager.get(uri, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String responseData = "";
                        Result<List<appointmentDto>> result = null;
                        try {

                            responseData = response.body().string();
                            System.out.println(responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        try {
                            result = objectMapper.readValue(responseData,new TypeReference<Result<List<appointmentDto>>>(){});
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        System.out.println(result);
                        System.out.println(result.getData());
                        if (result.getCode() == 1) {
                            publishNewOrderAdapter.setList(result.getData());

                        } else {
                            Toast.makeText(PublishNewOrderFragment.this.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

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