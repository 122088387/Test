package com.chaungying.use_car.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.calendar.cons.DPMode;
import com.chaungying.calendar.views.MonthView;
import com.chaungying.calendar.views.WeekView;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.T;
import com.chaungying.common.utils.date.DateUtil;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.modues.main.view.FillListView;
import com.chaungying.use_car.adapter.UseCarAdapter;
import com.chaungying.use_car.bean.AllCarBean;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 王晓赛 or 2016/6/29
 */
@ContentView(R.layout.activity_use_car)
public class UseCarActivity extends BaseActivity implements AdapterView.OnItemClickListener, MonthView.OnDateChangeListener, MonthView.OnDatePickedListener {


    @ViewInject(R.id.content_layout)
    private FillListView listView;

    @ViewInject(R.id.date_date)
    private TextView dateTv;

    // 月历控件
    @ViewInject(R.id.month_calendar)
    private MonthView monthView;

    // 星期控件
    @ViewInject(R.id.week_calendar)
    private WeekView weekView;


    // 日历数据
    private Calendar now;

    private String dateStr;


    private UseCarAdapter adapter;

    @ViewInject(R.id.title_back)
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        now = Calendar.getInstance();// 获取日历参数
        setActionBar(R.string.use_car, R.drawable.nav_return, R.drawable.icon_list);
        ImageView iv_right = (ImageView) findViewById(R.id.title_menu);
        // TitleBar右侧点击事件
        if (iv_right != null) {
            iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转会议预定记录
                    openActivty(UseCarActivity.this, UseCarRecordActivity.class, null, null);
                }
            });
        }
        createView();
        //创建适配器
        adapter = new UseCarAdapter(this);
        //连接网络获取数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(Calendar.getInstance().getTime());
        upData(date);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private String data;
    /**
     * 显示对话框
     */
    private void showProgressDialog() {
        //进度框的工具类
        ProgressUtil.show(UseCarActivity.this, "正在加载", false);
    }

    private List<AllCarBean> allCarBeanList;

    /**
     * 获取会议室的数据
     */
    private void upData(String date) {

        //显示刷新进度框
        showProgressDialog();
        ////////////////////////////联网获取数据/////////////////////
        RequestParams params = new RequestParams(Const.WuYe.URL_USER_CAR);
        params.setConnectTimeout(30 * 1000);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        final String date = sdf.format(calendar.getTime());
        params.addParameter("queryDate", date);
        //选择的日期
        this.data = date;
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                // TODO: 王晓赛 2016年8月02日 对数据进行解析  添加到适配器
                if (result != null && !result.equals("")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<AllCarBean>>() {
                    }.getType();
                    allCarBeanList = gson.fromJson(result, type);
                    adapter.setData(allCarBeanList);
                    //刷新适配器
                    adapter.notifyDataSetChanged();
                } else {
                    T.showShort(UseCarActivity.this, "没有数据");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showShort(UseCarActivity.this, "获取数据失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //关闭进度框
                ProgressUtil.close();
            }
        });

    }

    /**
     * 监听每条数据的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.metting_name);
        Bundle bundle = new Bundle();
        AllCarBean bean = allCarBeanList.get(position);
        bundle.putString("data", data);
        bundle.putSerializable("bean", bean);
        if (bean.getState() != 0) {//0是开放  1未开放
            T.showLong(this, "请选择可预订的车辆");
            return;
        }
        openActivty(this, UseCarSelectActivity.class, bundle, null);
    }

    @Event(value = R.id.title_back)
    private void back(View view) {
        finish();
    }

    /**
     * 初始化
     */
    private void createView() {
        // 初始化Content
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy年M月");
        dateTv.setText(formatterDate.format(new Date(System.currentTimeMillis())));
        // 月历模式配置
        monthView.setDPMode(DPMode.SINGLE);// 设置日期选择模式
        monthView.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1);//添加日期数据
        monthView.setFestivalDisplay(false);// 是否显示节日
        monthView.setTodayDisplay(true);//是否显示当天
        monthView.setOnDateChangeListener(this);// 日期改变监听
        monthView.setOnDatePickedListener(this);// 日期选择监听
        // 星期模式配置
        weekView.setDPMode(DPMode.SINGLE);//设置日期选择模式
        weekView.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1);// 添加日期数据
        weekView.setFestivalDisplay(false);// 是否显示节日
        weekView.setTodayDisplay(true);//是否显示当天
        weekView.setOnDatePickedListener(this);// 日期选择监听

        // 列表配置
        listView.setAdapter(adapter);

        dateStr = DateUtil.DateToString(new Date(System.currentTimeMillis()), "yyyy-MM-dd");

        upData(dateStr);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }

    /**
     * 日期改变监听
     *
     * @param year  年
     * @param month 月
     */
    @Override
    public void onDateChange(int year, int month) {
        String temp = "" + year + "年" + month + "月";
        dateTv.setText(temp);
    }

    /**
     * 日期选择监听
     *
     * @param date 日期
     */
    @Override
    public void onDatePicked(String date) {
        String temp = date.replace(".", "-");
        dateStr = DateUtil.StringToString(temp, "yyyy-MM-dd");
        upData(dateStr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dateStr != null && dateStr.length() > 0) {
            upData(dateStr);
        }
    }
}
