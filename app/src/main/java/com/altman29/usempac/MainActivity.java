package com.altman29.usempac;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.altman29.usempac.manager.Chart1Manager;
import com.altman29.usempac.manager.Chart2Manager;
import com.altman29.usempac.manager.Chart3Manager;
import com.altman29.usempac.widget.MySwitchTabLayout;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int count = 7;//7 or 14
    private CombinedChart chart1;
    private CombinedChart chart2;
    private CombinedChart chart3;
    private Chart1Manager chart1Manager;
    private Chart2Manager chart2Manager;
    private Chart3Manager chart3Manager;
    private MySwitchTabLayout timesTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //chart1
        chart1 = findViewById(R.id.chart1);
        chart2 = findViewById(R.id.chart2);
        chart3 = findViewById(R.id.chart3);
        chart1Manager = new Chart1Manager(chart1);
        chart2Manager = new Chart2Manager(chart2);
        chart3Manager = new Chart3Manager(chart3);
        timesTab = findViewById(R.id.mytab);

        timesTab.setOnItemCheckedChangeListener(new MySwitchTabLayout.OnItemCheckedChangeListener() {
            @Override
            public void onItemCheckedChange(boolean isChecked) {
                if (isChecked) {
                    switchCount(14);
                } else {
                    switchCount(7);
                }
            }
        });
        showMultiChart();
    }

    private void switchCount(int count) {
        //clear&retry
        chart1Manager.clear();
        chart2Manager.clear();
        chart3Manager.clear();
        this.count = count;
        showMultiChart();
    }

    public void showMultiChart() {
        // chart1
        CombinedData data1 = new CombinedData();
        data1.setData(fetch1_LineData());
        data1.setData(fetch1_BarData());
        //fix data(包括数据填充;x,y轴values显示,需加参数) & show； chart1
        chart1Manager.fixAxisWithDisplay(data1, count);

        // chart2
        CombinedData data2 = new CombinedData();
        data2.setData(fetch2_LineData());
        data2.setData(fetch2_BarData());
        //fix data(包括数据填充;x,y轴values显示,需加参数) & show； chart2
        chart2Manager.fixAxisWithDisplay(data2, count);

        // chart3
        CombinedData data3 = new CombinedData();
        data3.setData(fetch3_LineData());
        //fix data(包括数据填充;x,y轴values显示,需加参数) & show； chart3
        chart3Manager.fixAxisWithDisplay(data3, count);
    }

    private LineData fetch1_LineData() {
        ArrayList<Entry> entries = new ArrayList<>();
        //需要数据转换，(网络)外部传入数据，并处理
        for (int index = 0; index < count; index++) {
            entries.add(new Entry(index + 0.5f, getRandom(5, 0)));
        }
        return chart1Manager.generateLineData(entries);
    }

    private BarData fetch1_BarData() {
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        //需要数据转换，(网络)外部传入数据，并处理
        for (int index = 0; index < count; index++) {
            entries1.add(new BarEntry(index + 0.5f, getRandom(5, 0)));
            // stacked 注：俩个Bar而不是一个在其内。
            entries2.add(new BarEntry(index + 0.5f, new float[]{getRandom(2, 0), getRandom(3, 0)}));
        }
        return chart1Manager.generateBarData(entries1, entries2);
    }

    private LineData fetch2_LineData() {
        ArrayList<Entry> entries = new ArrayList<>();
        //需要数据转换，(网络)外部传入数据，并处理
        for (int index = 0; index < count; index++) {
            entries.add(new Entry(index + 0.5f, getRandom(5, 0)));
        }
        return chart2Manager.generateLineData(entries);
    }

    private BarData fetch2_BarData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        //需要数据转换，(网络)外部传入数据，并处理
        for (int index = 0; index < count; index++) {
            entries.add(new BarEntry(index + 0.5f, getRandom(5, 0)));
        }
        return chart2Manager.generateBarData(entries);
    }

    private LineData fetch3_LineData() {
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        //需要数据转换，(网络)外部传入数据，并处理
        for (int index = 0; index < count; index++) {
            entries1.add(new Entry(index + 0.5f, getRandom(4, 0)));
            entries2.add(new Entry(index + 0.5f, getRandom(4, 0)));
        }
        return chart3Manager.generateLineData1(entries1, entries2);
    }

    protected float getRandom(float range, float start) {
        //模拟数据
        return (float) (Math.random() * range) + start;
    }
}