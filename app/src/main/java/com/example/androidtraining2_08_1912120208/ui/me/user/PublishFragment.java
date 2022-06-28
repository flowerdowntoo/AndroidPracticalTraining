package com.example.androidtraining2_08_1912120208.ui.me.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidtraining2_08_1912120208.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class PublishFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_publish_my, container, false);
        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new PublishNewFragment());
        fragmentList.add(new PublishOldFragment());
        fragmentList.add(new PublishNewOrderFragment());
        TabLayout tabLayout=root.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2=root.findViewById(R.id.viewPager2);
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("新发布");
                    break;
                case 1:
                    tab.setText("已发布");
                    break;
                case 2:
                    tab.setText("新订单");
                    break;
            }
        });
        tabLayoutMediator.attach();
        return root;
    }
}