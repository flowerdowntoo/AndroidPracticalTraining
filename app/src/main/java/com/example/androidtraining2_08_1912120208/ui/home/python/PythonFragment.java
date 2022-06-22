package com.example.androidtraining2_08_1912120208.ui.home.python;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.adapter.PythonAdapter;
import com.example.androidtraining2_08_1912120208.base.BaseFragment2;

public class PythonFragment extends BaseFragment2 {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_python, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        //给recyclerView设置布局管理器，为线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //加入分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(),
                DividerItemDecoration.VERTICAL));
        PythonAdapter pythonAdapter=new PythonAdapter(null);
        recyclerView.setAdapter(pythonAdapter);
        PythonViewModel mViewModel = new ViewModelProvider(this).get(PythonViewModel.class);
        mViewModel.getPythonList().observe(getViewLifecycleOwner(), pythonAdapter::setList);
        return root;
    }

}