package com.chaungying.ji_xiao.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.ji_xiao.bean.PerSignBean;
import com.chaungying.ji_xiao.comm.SignAxisValueFormatter;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/9/19
 *         <p/>
 *         工作绩效中的签到
 */
@ContentView(R.layout.activity_per_sign)
public class PerSignActivity extends BaseActivity {

    @ViewInject(R.id.bar_chart)
    BarChart mChart;
    @ViewInject(R.id.sign_totalPeople)
    TextView sign_totalPeople;
    @ViewInject(R.id.sign_signInPeople)
    TextView sign_signInPeople;
    @ViewInject(R.id.sign_tardinessPeople)
    TextView sign_tardinessPeople;
    @ViewInject(R.id.sign_absenteeismPeople)
    TextView sign_absenteeismPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("签到分析", R.drawable.nav_return, 0);
        getData();

//        initView();
        //参数1：7天   总人数：是多少
//        setData(7, 5);

    }

    PerSignBean bean = null;

    /**
     * 联网获取数据
     */
    private void getData() {
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_SIGNIN_WORK_PERFORMANCE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                bean = new Gson().fromJson(result, PerSignBean.class);
                if (bean != null) {
                    initView();
                    //一星期    总人数是多少
                    setData(bean.getData().size(), bean.getTotalPeople());
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

    private void initView() {
        sign_totalPeople.setText(bean.getTotalPeople() + "");
        sign_signInPeople.setText(bean.getSignInPeople() + "");
        sign_tardinessPeople.setText(bean.getTardinessPeople() + "");
        sign_absenteeismPeople.setText(bean.getAbsenteeismPeople() + "");


        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        //设置描述
        mChart.setDescription("");
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(mChart); // For bounds control
//        mChart.setMarker(mv); // Set the marker to the chart

        //对图例的设置
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        l.setTypeface(CharBase.mTfLight);
        //图例距离Y轴的补偿距离
        l.setYOffset(0f);
        //两个图例之间的Y间隔
        l.setYEntrySpace(0f);
        //两个图例之间的X间隔
        l.setXEntrySpace(5f);
        l.setTextSize(8f);

        //对x轴进行设置
        XAxis xl = mChart.getXAxis();
        xl.setTypeface(CharBase.mTfLight);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setGranularity(1f);
        xl.setAxisMinValue(1);
        xl.setAxisMaxValue(8);
//        xl.setTextSize(5);
        xl.setCenterAxisLabels(true);
        xl.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String str = "";
                Log.i("aa", value + "");
                if (bean != null && value < 8) {
                    if (value != 0) {
                        str = bean.getData().get((int) value - 1).getDay();
                        str = str.substring(str.indexOf("-") + 1);
                    }
                }
                return str;
//                return String.valueOf((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        //对Y轴的属性设置
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(CharBase.mTfLight);
        leftAxis.setValueFormatter(new SignAxisValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1f);
        leftAxis.setSpaceTop(30f);
//        leftAxis.setTextSize(0);
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setDrawAxisLine(true);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setStartAtZero(true);
        mChart.getAxisRight().setEnabled(false);
    }

    private void setData(int xNum, float yRange) {

        float groupSpace = 0.04f;
        float barSpace = 0.08f; // x3 dataset
        float barWidth = 0.4f; // x3 dataset
        // (0.3 + 0.02) * 3 + 0.04 = 1.00 -> interval per "group"

        int startYear = 1;
        int endYear = startYear + xNum;


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        float mult = 100 * 5f;

        for (int i = startYear; i < endYear; i++) {
//            float val = (float) (Math.random() * mult) + 2;
            float val = bean.getData().get(i - 1).getAbsenteeismPeople2();
            yVals1.add(new BarEntry(i, val));
        }

        for (int i = startYear; i < endYear; i++) {
//            float val = (float) (Math.random() * mult) + 2;
            float val = bean.getData().get(i - 1).getSignInPeople2();
            yVals2.add(new BarEntry(i, val));
        }


        BarDataSet set1, set2;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValueTextSize(0);
            set1.setValues(yVals1);
            set2.setValueTextSize(0);
            set2.setValues(yVals2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create 3 datasets with different types
            set1 = new BarDataSet(yVals1, "未出勤人数");
            // set1.setColors(ColorTemplate.createColors(getApplicationContext(),
            // ColorTemplate.FRESH_COLORS));
            set1.setColor(Color.rgb(40, 151, 242));
            set1.setValueTextSize(0);
            set2 = new BarDataSet(yVals2, "出勤人数");
            set2.setColor(Color.rgb(245, 94, 78));
            set2.setValueTextSize(0);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new LargeValueFormatter());

            // add space between the dataset groups in percent of bar-width
            data.setValueTypeface(CharBase.mTfLight);

            mChart.setData(data);
        }

        mChart.getBarData().setBarWidth(barWidth);

//        mChart.getXAxis().setAxisMinimum(startYear);
//        mChart.getXAxis().setAxisMinValue(startYear);
//        mChart.getXAxis().setAxisMaximum(mChart.getBarData().getGroupWidth(groupSpace, barSpace) * 7 + startYear);
//        mChart.getXAxis().setAxisMaxValue(mChart.getBarData().getGroupWidth(groupSpace, barSpace) * xNum + startYear);
        mChart.getAxisLeft().setAxisMinValue(0);
        mChart.getAxisLeft().setAxisMaxValue(yRange);
        mChart.groupBars(startYear, groupSpace, barSpace);
        mChart.invalidate();

        mChart.animateY(2000);
    }

}
