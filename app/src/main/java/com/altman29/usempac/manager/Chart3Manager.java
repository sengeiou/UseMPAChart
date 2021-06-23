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
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright©  2021
 * 正岸健康
 * author: csy
 * created on: 6/21/21 6:08 PM
 * description:
 */
public class Chart3Manager {

    private CombinedChart chart;
    private boolean isShowValues = false;//Bar,Line是否显示数值
    private Legend legend;

    public Chart3Manager(CombinedChart chart) {
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
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setGranularity(1);
        rightAxis.setTextColor(Color.parseColor("#FFC4C4CC"));
        rightAxis.setTextSize(12f);
        rightAxis.setAxisMaximum(5f);//基本坐标0-5，真实值在setValuesFormatter中设置
        rightAxis.setYOffset(-10f);//right Y values向上偏移
        rightAxis.setDrawAxisLine(false);//只画值，不画线

        //left Y
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setGranularity(1f);
        leftAxis.setTextColor(Color.parseColor("#FFC4C4CC"));
        leftAxis.setTextSize(12f);
        leftAxis.setAxisMaximum(4.5f);//基本坐标0-5，真实值在setValuesFormatter中设置
        leftAxis.setGridLineWidth(0.5f);
        leftAxis.setYOffset(-10f);//left Y values向上偏移
        leftAxis.setGridDashedLine(new DashPathEffect(new float[]{8f, 8f}, 8f));
        leftAxis.setGridColor(Color.parseColor("#E1E1E1"));
        leftAxis.setDrawAxisLine(false);//只画值，不画线

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
                "睡眠满意度",
                Legend.LegendForm.LINE,
                8f,
                2f,
                null,
                Color.parseColor("#FF72A4E1"))
        );
        entries.add(new LegendEntry(
                "日间满意度",
                Legend.LegendForm.LINE,
                8f,
                2f,
                null,
                Color.parseColor("#FF4FB6A6"))
        );

        legend.setCustom(entries);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setXEntrySpace(16f);
        legend.setTextSize(12f);
        legend.setXOffset(-40f);
    }

    /**
     * 生成LindeData
     *
     * @param entries1
     * @param entries2
     * @return
     */
    public LineData generateLineData1(ArrayList<Entry> entries1, ArrayList<Entry> entries2) {

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        LineDataSet set1 = new LineDataSet(entries2, "日间满意度");
        set1.setColor(Color.parseColor("#FF72A4E1"));
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);//三次贝塞尔曲线
        set1.setDrawValues(isShowValues);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.parseColor("#FFC4C4CC"));
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSets.add(set1);

        LineDataSet set2 = new LineDataSet(entries1, "睡眠满意度");
        set2.setColor(Color.parseColor("#FF4FB6A6"));
        set2.setLineWidth(2f);
        set2.setDrawCircles(false);
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);//三次贝塞尔曲线
        set2.setDrawValues(isShowValues);
        set2.setValueTextSize(10f);
        set2.setValueTextColor(Color.parseColor("#FFC4C4CC"));
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSets.add(set2);

        LineData d = new LineData(dataSets);
        return d;
    }

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
                    if (index % 2 == 1) {
                        return "";
                    } else {
                        return (int) value + "";
                    }
                } else {
                    return (int) value + "";
                }
            }
        });

        //左侧Y轴显示 已处理
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //0,1,2,3,4
                switch ((int) value) {
                    case 0:
                        return "非常差";
                    case 1:
                        return "差";
                    case 2:
                        return "一般";
                    case 3:
                        return "好";
                    case 4:
                        return "非常好";
                    default:
                        return "default";
                }
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