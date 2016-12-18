package com.chaungying.ji_xiao.ui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.view.DownPopWindowPerView;
import com.chaungying.ji_xiao.adapter.JobPerformanceAdaper;
import com.chaungying.ji_xiao.bean.JobHeader;
import com.chaungying.ji_xiao.bean.JobPer;
import com.chaungying.ji_xiao.bean.JobPerCon;
import com.chaungying.ji_xiao.bean.PerRepairDispatchBean;
import com.chaungying.site_repairs.view.PullToRefreshLayout;
import com.chaungying.wuye3.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作绩效  中的维修派工
 *
 * @author houbopeng 2016.8.17
 */
@ContentView(R.layout.activity_job_performance)
public class PerRepairDispatchActivity extends BaseActivity implements OnChartValueSelectedListener {

    protected String[] mParties = new String[]{"", "", "", "", "", "", "", ""};

    /**
     * 标题的父布局
     */
//    @ViewInject(R.id.title_actionbar2)
//    private RelativeLayout relativeLayout;
//    /**
//     * 当天
//     */
//    @ViewInject(R.id.day)
//    private TextView titleDay;
//    /**
//     * 当月
//     */
//    @ViewInject(R.id.month)
//    private TextView titleNonth;
    /**
     * 选择月和天的标示
     */
    private int select = 0;


    /**
     * 刷新
     */
//    @ViewInject(R.id.refresh_view)
//    private PullToRefreshLayout pullToRefreshLayout;

    /**
     * 头部标题
     */
    @ViewInject(R.id.job_add_ll)
    private LinearLayout job_add_ll;

    /**
     * 列表
     */
    @ViewInject(R.id.content_view)
    private ListView listView;
    /**
     * 适配器
     */
    private JobPerformanceAdaper adapter;


    /////////////////图表//////////////////
    @ViewInject(R.id.pieChart1)
    private PieChart mPieChart;
    @ViewInject(R.id.pieChart2)
    private PieChart mPieChart2;

    ArrayList<Integer> colors1 = new ArrayList<Integer>();
    ArrayList<Integer> colors2 = new ArrayList<Integer>();
    ArrayList<String> lables = new ArrayList<>();
    /**
     * 数据集合
     */
    private List<JobPerCon> jobs = new ArrayList<JobPerCon>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter.setJobs(jobs);
                    adapter.notifyDataSetChanged();
                    break;
                case 2://点击某项之后请求的数据
                    Bundle bundle = msg.getData();
                    JobPer jobPer = (JobPer) bundle.getSerializable("bean");
                    ArrayList<JobPerCon> list = jobPer.data;
                    JobPerCon jobPerCon = new JobPerCon();
                    jobPerCon.name = "合计：";
                    jobPerCon.total = jobPer.totalSum;
                    jobPerCon.finish = jobPer.finishSum;
                    jobPerCon.percent = jobPer.percentSum;
                    list.add(jobPerCon);
                    adapter.setJobs(list);
                    adapter.notifyDataSetChanged();
                    break;
                case 3:
                    PerRepairDispatchBean perRepairDisBean = (PerRepairDispatchBean) msg.getData().getSerializable("bean");
                    setData(mPieChart, colors1, perRepairDisBean.getFinishSum(), perRepairDisBean.getUnfinishedSum());
                    setData(mPieChart2, colors2, perRepairDisBean.getUnfinishedSum(), perRepairDisBean.getThirtyMinutesSum(), perRepairDisBean.getOneHourSum(), perRepairDisBean.getOneDaySum(), perRepairDisBean.getMoreOneDaySum());
                    break;
            }
//            if(msg.what==0){
//                pullToRefreshLayout.refreshFinish(0);
//            }else{
//                pullToRefreshLayout.refreshFinish(1);
//            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            //沉浸式
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        x.view().inject(this);//注入Vie我和事件
        setActionBar("维修派工", R.drawable.nav_return, 0);
        initColors();
        mPieChart.setTag(1);
        mPieChart2.setTag(2);
        initView(mPieChart);
        initView(mPieChart2);
        initDate();
    }

    private void initColors() {
        colors1.add(Color.rgb(0, 223, 199));
        colors1.add(Color.rgb(46, 179, 248));

        colors2.add(Color.rgb(182, 160, 255));
        colors2.add(Color.rgb(248, 126, 46));
        colors2.add(Color.rgb(253, 117, 163));
        colors2.add(Color.rgb(46, 179, 248));
        colors2.add(Color.rgb(255, 199, 79));
    }

    private void initView(PieChart mPieChart) {
        //饼状
        mPieChart.setUsePercentValues(true);
        //没有该方法
//        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        //拖拽转动时减速
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setDescription("");
        //没有该方法
//        mPieChart.setCenterTextTypeface(mTfLight);
//        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setHoleColor(Color.WHITE);

        //洞 Hole
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(0);

        //设置中间圆洞的弧度
        mPieChart.setHoleRadius(0f);
        //设置圆洞外半透明的圆弧
        mPieChart.setTransparentCircleRadius(0f);

        // 绘制中间的文字
        mPieChart.setDrawCenterText(false);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);
    }

    private void setData(PieChart mPieChart, ArrayList<Integer> colors, int... num) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < num.length; i++) {

//            entries.add(new PieEntry((float) ((num[i] / 100.0 * mult) + mult / 5), mParties[i % mParties.length]));
            entries.add(new PieEntry((float) (num[i]), mParties[i % mParties.length]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        //设置两片之间的空白间隙
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);

        // 设置每一扇形的颜色
        if ((int) mPieChart.getTag() == 1) { //完成率分析
            colors = colors1;
        } else { //效率分析
            colors = colors2;
        }
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(CharBase.mTfLight);
        mPieChart.setData(data);
        //从哪个位置开始扇形动画
        mPieChart.setRotationAngle(270);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        /////////////////////////////因为设置在下部初始化时不显示，目前不使用//////////////////////
        //图例
        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTypeface(CharBase.mTfLight);
        if ((int) mPieChart.getTag() == 1) {
            lables.clear();
            lables.add("完成数量");
            lables.add("未完成数量");
            //添加额外的标签和和颜色
            l.setExtra(colors, lables);
        } else {
            lables.clear();
            lables.add("完成数量");
            lables.add("半小时");
            lables.add("一小时");
            lables.add("一天内");
            lables.add("一天以上");
            l.setExtra(colors, lables);
        }
        l.setYOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        //隐藏图例
        l.setEnabled(false);

        // entry label styling  饼形状图上的标签设置不包含%号
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTypeface(CharBase.mTfRegular);
        mPieChart.setEntryLabelTextSize(0f);
        //不绘制标签
        mPieChart.setDrawEntryLabels(false);
    }

    /**
     * 标题点击事件
     *
     * @param v
     */
//    public void doClick(View v) {
//        switch (v.getId()) {
//            case R.id.day://当天
//                select = 0;
//                break;
//            case R.id.month://当月
//                select = 1;
//                break;
//        }
//        selectTitle(select);
//    }


    /**
     * @param arg 0当天1当月
     */
//    private void selectTitle(int arg) {
//        switch (arg) {
//            case 0://当天
//                titleDay.setTextColor(getResources().getColor(R.color.blue_dark));
//                titleDay.setBackgroundResource(R.drawable.title_round_white_left);
//
//                titleNonth.setTextColor(getResources().getColor(R.color.White));
//                titleNonth.setBackgroundResource(R.drawable.title_round_blue_right);
//                break;
//            case 1://当月
//                titleDay.setTextColor(getResources().getColor(R.color.White));
//                titleDay.setBackgroundResource(R.drawable.title_round_blue_left);
//                titleNonth.setTextColor(getResources().getColor(R.color.blue_dark));
//                titleNonth.setBackgroundResource(R.drawable.title_round_white_right);
//                break;
//        }
//    }

    /**
     * 初始化数据
     */
    private void initDate() {
//        selectTitle(select);
//        relativeLayout.setVisibility(View.VISIBLE);
        adapter = new JobPerformanceAdaper(this);
        adapter.setJobs(jobs);
        listView.setAdapter(adapter);

//        pullToRefreshLayout.setOnRefreshListener(onRefreshListener);
        requestScreen();
        request();
    }

    /**
     * 请求员工绩效筛选头
     */
    private void requestScreen() {
        RequestParams params = new RequestParams(Const.WuYe.URL_JOP_PER_HEADER);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JobHeader header = null;
                try {
                    header = new Gson().fromJson(result, JobHeader.class);
                } catch (Exception e) {// Json转换异常
                    e.printStackTrace();
                }
                if (header != null && header.getRespCode() == 1001) {
                    List<JobHeader.ItemsBean> dataBean = header.getItems();
                    DownPopWindowPerView downPopWindowPerView = new DownPopWindowPerView(PerRepairDispatchActivity.this);
                    downPopWindowPerView.setItemsBeanList(dataBean);
                    downPopWindowPerView.setHandler(handler);
                    job_add_ll.addView(downPopWindowPerView);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    PerRepairDispatchBean perRepairDisBean;

    /**
     * 刚进入界面请求数据  请求员工筛选默认的总数据
     */
    private void request() {
        RequestParams params = new RequestParams(Const.WuYe.URL_JOP_PER);
        params.setConnectTimeout(30 * 1000);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                JobPer jobper = gson.fromJson(result, JobPer.class);
                if (jobper != null) {
                    if (jobper.respCode == 1001) {//成功
//                        jobs.addAll(jobper.data);
                        JobPerCon jobPerCon = new JobPerCon();
                        jobPerCon.name = "合计：";
                        jobPerCon.total = jobper.totalSum;
                        jobPerCon.finish = jobper.finishSum;
                        jobPerCon.percent = jobper.percentSum;
                        jobper.data.add(jobPerCon);
//                        View view = LayoutInflater.from(PerRepairDispatchActivity.this).inflate(R.layout.activity_job_performance_footer, null);
//                        listView.addFooterView();
                        adapter.setJobs(jobper.data);
                        adapter.notifyDataSetChanged();
//                        pullToRefreshLayout.refreshFinish(0);
//                        Message m = new Message();
//                        m.what = 0;

//                        hand.sendMessage(m);

                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Message m = new Message();
                m.what = 1;
                handler.sendMessage(m);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        //对于图表数据的请求
        RequestParams params1 = new RequestParams(Const.WuYe.URL_SIGNIN_WORK_REPAIRCHARTS_LIST);
//        date=时间id&departmentId=部门id
        params.addParameter("date", "");
        params.addParameter("departmentId", "");
        params1.setConnectTimeout(30 * 1000);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                perRepairDisBean = gson.fromJson(result, PerRepairDispatchBean.class);
                setData(mPieChart, colors1, perRepairDisBean.getFinishSum(), perRepairDisBean.getUnfinishedSum());
                setData(mPieChart2, colors2, perRepairDisBean.getUnfinishedSum(), perRepairDisBean.getThirtyMinutesSum(), perRepairDisBean.getOneHourSum(), perRepairDisBean.getOneDaySum(), perRepairDisBean.getMoreOneDaySum());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 刷新
     */
    private PullToRefreshLayout.OnRefreshListener onRefreshListener = new PullToRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            jobs.clear();
            request();
//            new GetDataTask().execute();
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        }
    };

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
//            pullToRefreshLayout.refreshFinish(0);

            super.onPostExecute(result);
        }
    }

    /**
     * 返回
     *
     * @param v
     */
    @Event(value = R.id.title_back)
    private void back(View v) {
        finish();
    }

//    /**
//     * 点击加号
//     *
//     * @param view
//     */
//    @Event(value = R.id.title_menu)
//    private void add(View view) {
//        Log.e("fd","点击了");
//        openActivty(this, ChartActivity.class, null, null);
//    }
}
