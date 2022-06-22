package com.example.androidtraining2_08_1912120208.ui.chart.line;

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
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
public class LineFragment extends BaseFragment2 {
    private LineViewModel mViewModel;
    public static LineFragment newInstance() {
        return new LineFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_line, container, false);
        LineChart chart = root.findViewById(R.id.lineChart);
        LineViewModel lineViewModel=new ViewModelProvider(this).get(LineViewModel.class);
        lineViewModel.getLineList().observe(getViewLifecycleOwner(),lineBeans -> {
            //添加数据
            List<Entry> entries = new ArrayList<Entry>();
            for(int i=0;i<lineBeans.size();i++){
                entries.add(new Entry(i,lineBeans.get(i).getSalaries()));
            }
            //实体集
            LineDataSet dataSet = new LineDataSet(entries, "工资"); // add entries to dataset
            dataSet.setColor(Color.BLUE);//Color.rgb() 分割线颜色
            dataSet.setValueTextColor(Color.RED); // styling, ... 字体颜色
            dataSet.setValueTextSize(12f);
            dataSet.setLineWidth(6f);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.invalidate(); // refresh
            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setAxisLineColor(Color.BLACK);
            xAxis.setAxisLineWidth(3f);
            xAxis.enableGridDashedLine(10f,10f,0f);
// set a custom value formatter
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return lineBeans.get((int)value).getYear();
                }
            });
            chart.getAxisRight().setEnabled(false);
            YAxis yAxis = chart.getAxisLeft();
            yAxis.setTextColor(Color.BLACK);
            yAxis.setAxisLineColor(Color.BLACK);
            yAxis.setAxisLineWidth(3f);
            yAxis.enableGridDashedLine(10f,10f,0f);
            yAxis.setAxisMinimum(0f);
            yAxis.setSpaceTop(38.2f);
            // data has AxisDependency.LEFT
            //参考线
            LimitLine limitLine=new LimitLine(10000f,"厦门市平均工资");
            limitLine.setLineColor(Color.MAGENTA);
            limitLine.setLineWidth(2f);
            yAxis.addLimitLine(limitLine);
            Legend l = chart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            // and many more...
            // enable or disable the description
            Description description = chart.getDescription();
// set the description text
            description.setTextAlign(Paint.Align.CENTER);
            description.setText("java工程师经验与工资的对应情况");
            description.setTextSize(16f);
            description.setTextColor(Color.BLACK);
// set the position of the description on the screen
            description.setPosition(540f, 100f);
            chart.animateX(5000);
//... and more
// and more...
        });
        return root;
    }
}