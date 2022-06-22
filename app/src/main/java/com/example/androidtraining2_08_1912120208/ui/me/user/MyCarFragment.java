package com.example.androidtraining2_08_1912120208.ui.me.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.androidtraining2_08_1912120208.R;


public class MyCarFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root2=inflater.inflate(R.layout.fragment_car_content, container, false);
        View root=inflater.inflate(R.layout.fragment_my_car, container, false);

        LinearLayout linearLayout_car = root.findViewById(R.id.mycar_item);
        linearLayout_car.setOnClickListener(this::click);
        return root;
    }

    private void click(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable.ic_launcher);

        builder.setTitle("选择操作");
        //    指定下拉列表的显示数据
        final String[] c = {"详情","删除"};
        //    设置一个下拉的列表选择项
        builder.setItems(c, new DialogInterface.OnClickListener()
        {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

//                Toast.makeText(getActivity(), "选择的操作为：" + c[which], Toast.LENGTH_SHORT).show();
                if(c[which]=="详情"){

                    Navigation.findNavController(view).navigate(R.id.action_myCarFragment_to_carMessage);
//                    Toast.makeText(getContext(), "跳转", Toast.LENGTH_SHORT).show();
                }

            }
        });
        AlertDialog r_dialog = builder.create();
        r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        r_dialog.show();


    }
}