package com.altman29.usempac.manager;

import android.graphics.Color;
import android.graphics.DashPathEffect;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright©  2021
 * 正岸健康
 * author: csy
 * created on: 6/21/21 5:12 PM
 * description:
 */
public class Chart1Manager {

    private CombinedChart chart;
    private boolean isShowValues = false;//Bar,Line是否显示数值
    private Legend legend;

    public Chart1Manager(CombinedChart chart) {
        this.chart = chart;
        init();
    }

    private void init() {

        //basic settings
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setScaleEnabled(false);
        chart.setTouchEnabled(false);
        chart.setExtraBottomOffset(14f);
        chart.animateY(500);

        // draw order
        chart.setDrawOrder(new DrawOrder[]{
                DrawOrder.BAR, DrawOrder.LINE
        });

        //设置图例相关
        legend = chart.getLegend();
        setCustomLegend();

        //right Y
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setGranularity(1);
        rightAxis.setTextColor(Color.parseColor("#FFC4C4CC"));
        rightAxis.setTextSize(12f);
        rightAxis.setAxisMaximum(5.5f);//基本坐标0-5，真实值在setValuesFormatter中设置
        rightAxis.setYOffset(-10f);//right Y values向上偏移
        rightAxis.setDrawAxisLine(false);

        //left Y
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setGranularity(1f);
        leftAxis.setTextColor(Color.parseColor("#FFC4C4CC"));
        leftAxis.setTextSize(12f);
        leftAxis.setAxisMaximum(5.5f);//基本坐标0-5，真实值在setValuesFormatter中设置
        leftAxis.setGridLineWidth(0.5f);
        leftAxis.setYOffset(-10f);//left Y values向上偏移
        leftAxis.setGridDashedLine(new DashPathEffect(new float[]{8f, 8f}, 8f));
        leftAxis.setGridColor(Color.parseColor("#E1E1E1"));
        leftAxis.setDrawAxisLine(false);

        //X
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);//X轴设置在底部
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);//只画值，不画线
        xAxis.setAxisMinimum(0f);//最小0
        xAxis.setGranularity(1f);//粒度1
        xAxis.setTextColor(Color.parseColor("#FFC4C4CC"));
        xAxis.setTextSize(12f);
        xAxis.setCenterAxisLabels(true);//针对每个X坐标，X的坐标显示居中
    }

    public void setCustomLegend() {
        List<LegendEntry> entries = new ArrayList<>();
        entries.add(new LegendEntry());
        entries.add(new LegendEntry(
                "卧床时长",
                Legend.LegendForm.CIRCLE,
                8f,
                9f,
                null,
                Color.parseColor("#FFE4E2FB"))
        );
        entries.add(new LegendEntry(
                "睡眠时长",
                Legend.LegendForm.CIRCLE,
                8f,
                0f,
                null,
                Color.parseColor("#FF7B72E1"))
        );
        entries.add(new LegendEntry(
                "约定时长",
                Legend.LegendForm.CIRCLE,
                8f,
                0f,
                null,
                Color.parseColor("#FF72A4E1")));
        entries.add(new LegendEntry(
                "睡眠效率",
                Legend.LegendForm.LINE,
                8f,
                2f,
                null,
                Color.parseColor("#FFFFC166")));

        legend.setCustom(entries);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setXEntrySpace(16f);
        legend.setTextSize(12f);
        legend.setXOffset(-20f);

    }

    /**
     * 生成LindeData
     *
     * @param entries
     * @return
     */
    public LineData generateLineData(ArrayList<Entry> entries) {
        LineData d = new LineData();
        LineDataSet set = new LineDataSet(entries, "睡眠效率");
        set.setColor(Color.parseColor("#FFFFC166"));
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);//三次贝塞尔曲线
        set.setDrawValues(isShowValues);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.parseColor("#FFC4C4CC"));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);
        return d;
    }

    /**
     * 生成BarData with stacked
     *
     * @param entries1
     * @param entries2
     * @return
     */
    public BarData generateBarData(ArrayList<BarEntry> entries1, ArrayList<BarEntry> entries2) {

        BarDataSet set1 = new BarDataSet(entries1, "约定时长");
        set1.setColor(Color.parseColor("#FF72A4E1"));
        set1.setDrawValues(isShowValues);
        set1.setValueTextColor(Color.parseColor("#FFC4C4CC"));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setStackLabels(new String[]{"睡眠时长", "卧床时长"});
        set2.setColors(Color.parseColor("#FF7B72E1"), Color.parseColor("#FFE4E2FB"));
        set2.setDrawValues(isShowValues);
        set2.setValueTextColor(Color.parseColor("#FFC4C4CC"));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.3f;
        float barSpace = 0.1f;
        float barWidth = 0.25f;
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set2, set1);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }

    //需要扩展参数列表，来控制两侧Y轴，X轴具体值显示
    public void fixAxisWithDisplay(CombinedData data, int count) {
        chart.setData(data);

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMaximum(data.getXMax() + 0.5f);//just show nice
        xAxis.setLabelCount(count, false);

        //底部X轴显示
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                if (count == 14) {
                    if (index % 2 == 1) {//14 隔一显示
                        return "";
                    } else {
                        return (int) value + "";
                    }
                } else {
                    return (int) value + "";
                }
            }
        });

        //左侧Y轴显示
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value * 2 + "";
            }
        });

        //右侧Y轴显示
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value * 20 + "%";
            }
        });
        //show
        chart.invalidate();
    }

    public void clear() {
        chart.clear();
        chart.animateY(500);
    }
}