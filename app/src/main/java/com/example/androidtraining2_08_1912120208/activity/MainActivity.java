package com.example.androidtraining2_08_1912120208.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.base.BaseFragment2;
import com.example.androidtraining2_08_1912120208.base.OnFragmentKeyDownListener;
import com.example.androidtraining2_08_1912120208.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private  AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private List<OnFragmentKeyDownListener> onFragmentKeyDownListenerList=new ArrayList<>();
    private long exitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
         appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_chart,
                R.id.navigation_video,R.id.navigation_me)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(onFragmentKeyDownListenerList.size()>0){
            onFragmentKeyDownListenerList.remove(onFragmentKeyDownListenerList.size()-1);
        }
        return NavigationUI.navigateUp(navController,appBarConfiguration);
    }

    public void setOnFragmentKeyDownListener(OnFragmentKeyDownListener onFragmentKeyDownListener) {
        onFragmentKeyDownListenerList.add(onFragmentKeyDownListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下返回键
        if(event.getAction()==KeyEvent.ACTION_DOWN&&keyCode==KeyEvent.KEYCODE_BACK){
            if(onFragmentKeyDownListenerList.size()!=0){
                if(onFragmentKeyDownListenerList.get(onFragmentKeyDownListenerList.size()-1)
                    .onKeyDown(keyCode, event)){
                    return  true;
                }else{
                    onSupportNavigateUp();
                }
            }else if(System.currentTimeMillis()-exitTime>2000){
                Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();
            }
            else{
                //退出
               //真退出 finish();
                //假退出
                Intent intent=new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);

            }
            return  true;
        }
        return  super.onKeyDown(keyCode, event);
    }
}