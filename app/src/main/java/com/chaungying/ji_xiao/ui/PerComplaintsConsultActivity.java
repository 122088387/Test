package com.chaungying.ji_xiao.ui;

import android.graphics.Color;
import android.os.Bundle;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.ji_xiao.bean.PerComplaintsConsultBean;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/9/20
 *         <p/>
 *         绩效中的投诉咨询投
 */
@ContentView(R.layout.activity_per_complaints_consult)
public class PerComplaintsConsultActivity extends BaseActivity {

    @ViewInject(R.id.combined_chart)
    private CombinedChart mChart;

    @ViewInject(R.id.combined_chart2)
    private CombinedChart chart2;

    private int itemcount = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("投诉咨询", R.drawable.nav_return, 0);
        initData();
    }

    PerComplaintsConsultBean bean;

    private void initData() {
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_SIGNIN_WORK_SERVICE_WORK_PERFORMANCE_LIST);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                bean = new Gson().fromJson(result, PerComplaintsConsultBean.class);
                if (bean != null) {
                    itemcount = bean.getListComplaint().size();
                    mChart.setTag(1);
                    chart2.setTag(2);
                    initView(mChart);
                    initView(chart2);
                }
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ProgressUtil.close();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initView(CombinedChart mChart) {
        mChart.setDescription("");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setStartAtZero(true);
        rightAxis.setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setStartAtZero(true);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMinValue(0);
        xAxis.setTypeface(CharBase.mTfLight);
        xAxis.setAxisMaxValue(itemcount + 1);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String str = "";
                if (value < itemcount+1 && value != 0) {
                    str = bean.getListComplaint().get((int) (value - 1)).getDay();
                    str = str.substring(str.indexOf("-") + 1);
                }
//                return mMonths[(int) value % mMonths.length];
                return str;
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        CombinedData data = new CombinedData();

        data.setData(generateLineData(mChart));
        data.setData(generateBarData(mChart));
        //气泡
//        data.setData(generateBubbleData());
        //散列
//        data.setData(generateScatterData());
        //蜡烛
//        data.setData(generateCandleData());
        data.setValueTypeface(CharBase.mTfLight);

        mChart.setData(data);
        mChart.animateX(2000, Easing.EasingOption.EaseInOutSine);
        mChart.invalidate();
    }


    private LineData generateLineData(CombinedChart mChart) {

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        l.setTextSize(10f);

        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 1; index < itemcount + 1; index++)
            if ((int)mChart.getTag() == 1) {
                entries.add(new Entry(index, bean.getListComplaint().get(index - 1).getFinish()));
            } else {
                entries.add(new Entry(index, bean.getListAnswer().get(index - 1).getFinish()));
            }

        LineDataSet set = new LineDataSet(entries, "解决量");
        set.setColor(Color.rgb(40, 151, 242));
        set.setLineWidth(1.5f);
        set.setCircleColor(Color.rgb(40, 151, 242));
        set.setCircleRadius(4f);
        set.setFillColor(Color.rgb(40, 151, 242));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(0f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);
        return d;
    }

    private BarData generateBarData(CombinedChart mChart) {

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        l.setTextSize(10f);

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        for (int index = 1; index < itemcount + 1; index++) {
            if ((int)mChart.getTag() == 1) {
                entries1.add(new BarEntry(index, bean.getListComplaint().get(index - 1).getTotal()));
            } else {
                entries1.add(new BarEntry(index, bean.getListAnswer().get(index - 1).getTotal()));
            }

            // stacked
//            entries2.add(new BarEntry(0, new float[]{getRandom(13, 12), getRandom(13, 12)}));
        }

        BarDataSet set1 = new BarDataSet(entries1, "投诉总量");
        if((int)mChart.getTag() == 1){
            set1.setLabel("投诉总量");
        }else{
            set1.setLabel("咨询总量");
        }
        set1.setColor(Color.rgb(255, 0, 0));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(0f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

//        BarDataSet set2 = new BarDataSet(entries2, "");
//        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
//        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
//        set2.setValueTextColor(Color.rgb(61, 165, 255));
//        set2.setValueTextSize(10f);
//        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        set1.setBarBorderWidth(barSpace);
        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);
        // make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace); // start at x = 0
        return d;
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }


    protected ScatterData generateScatterData() {

        ScatterData d = new ScatterData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (float index = 0; index < itemcount; index += 0.5f)
            entries.add(new Entry(index + 0.25f, getRandom(10, 55)));

        ScatterDataSet set = new ScatterDataSet(entries, "Scatter DataSet");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setScatterShapeSize(7.5f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        d.addDataSet(set);

        return d;
    }

    protected CandleData generateCandleData() {

        CandleData d = new CandleData();

        ArrayList<CandleEntry> entries = new ArrayList<CandleEntry>();

        for (int index = 0; index < itemcount; index += 2)
            entries.add(new CandleEntry(index + 1f, 90, 70, 85, 75f));

        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
        set.setDecreasingColor(Color.rgb(142, 150, 175));
        set.setShadowColor(Color.DKGRAY);
        set.setBarSpace(0.3f);
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        d.addDataSet(set);

        return d;
    }

    protected BubbleData generateBubbleData() {

        BubbleData bd = new BubbleData();

        ArrayList<BubbleEntry> entries = new ArrayList<BubbleEntry>();

        for (int index = 0; index < itemcount; index++) {
            float y = getRandom(10, 105);
            float size = getRandom(100, 105);
            entries.add(new BubbleEntry(index + 0.5f, y, size));
        }

        BubbleDataSet set = new BubbleDataSet(entries, "Bubble DataSet");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.WHITE);
        set.setHighlightCircleWidth(1.5f);
        set.setDrawValues(true);
        bd.addDataSet(set);

        return bd;
    }


}
