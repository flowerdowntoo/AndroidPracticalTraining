package com.example.androidtraining2_08_1912120208.ui.chart.pie;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidtraining2_08_1912120208.R;
import com.example.androidtraining2_08_1912120208.base.BaseFragment2;
import com.example.androidtraining2_08_1912120208.ui.chart.line.LineFragment;
import com.example.androidtraining2_08_1912120208.ui.chart.line.LineViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class PieFragment extends BaseFragment2 {

    private LineViewModel mViewModel;

    public static LineFragment newInstance() {
        return new LineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_pie, container, false);
        PieChart chart = root.findViewById(R.id.PieChart);
       PieViewModel pieViewModel=new ViewModelProvider(this).get(PieViewModel.class);
        pieViewModel.getPieList().observe(getViewLifecycleOwner(),pieBeans -> {

            //添加数据
            List<PieEntry> entries = new ArrayList<>();
            for(int i=0;i<pieBeans.size();i++){
                entries.add(new PieEntry(pieBeans.get(i).getPercentage(),pieBeans.get(i).getSalaries()));
            }
            //实体集
            PieDataSet dataSet = new PieDataSet(entries, "工资占比"); // add entries to dataset
            dataSet.setColors(Color.GRAY,Color.GREEN,Color.MAGENTA,Color.CYAN);//Color.rgb() 分割线颜色
            dataSet.setValueTextColor(Color.WHITE); // styling, ... 字体颜色
            dataSet.setValueTextSize(12f);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return value+"%";
                }
            });
            PieData pieData = new PieData(dataSet);
            chart.setData(pieData);
            chart.setCenterText("点击显示\n相关数据");
            chart.setCenterTextColor(Color.BLACK);
            chart.setCenterTextSize(24f);
            chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    chart.setCenterText(((PieEntry)e).getLabel()+"\n"+((PieEntry)e).getValue()+"%");
                }

                @Override
                public void onNothingSelected() {
                    chart.setCenterText("点击显示\n相关数据");
                }
            });
            chart.setExtraTopOffset(10f);
            chart.invalidate(); // refresh
            Legend l = chart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            // and many more...
            // enable or disable the description
            Description description = chart.getDescription();
// set the description text
            description.setTextAlign(Paint.Align.CENTER);
            description.setText("Android工程师经验与工资的对应情况");
            description.setTextSize(16f);
            description.setTextColor(Color.BLACK);
// set the position of the description on the screen
            description.setPosition(540f, 100f);
            chart.animateXY(5000,5000);




//... and more
// and more...

        });
        return root;
    }



}