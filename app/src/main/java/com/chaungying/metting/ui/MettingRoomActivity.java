package com.chaungying.metting.ui;

import android.app.ProgressDialog;
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
import com.chaungying.metting.adapter.MettingRoomAdapter;
import com.chaungying.metting.bean.MettingRoomBean;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.modues.main.view.FillListView;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wefika.calendar.manager.CalendarManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 王晓赛 or 2016/6/29
 */
@ContentView(R.layout.activity_metting_room)
public class MettingRoomActivity extends BaseActivity implements AdapterView.OnItemClickListener, MonthView.OnDateChangeListener, MonthView.OnDatePickedListener {

    private CalendarManager manager;

//    @ViewInject(R.id.metting_calendar)
//    private CollapseCalendarView calendarView;

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


    private MettingRoomAdapter adapter;
    //会议室的数据
    private List<MettingRoomBean> roomList = new ArrayList<>();

    @ViewInject(R.id.title_back)
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        now = Calendar.getInstance();// 获取日历参数
        setActionBar(R.string.metting_room, R.drawable.nav_return, R.drawable.icon_list);
        ImageView iv_right = (ImageView) findViewById(R.id.title_menu);
        // TitleBar右侧点击事件
        if (iv_right != null) {
            iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转会议预定记录
                    openActivty(MettingRoomActivity.this, MettingRoomRecordActivity.class, null, null);
                }
            });
        }
        createView();
        //创建适配器
        adapter = new MettingRoomAdapter(this);
        //连接网络获取数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(Calendar.getInstance().getTime());
        upData(date);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        //获取日历的管理者
//        CalendarManager manager = new CalendarManager(LocalDate.now(), CalendarManager.State.WEEK, LocalDate.now(), LocalDate.now().plusYears(1));
//        calendarView.init(manager);
        //点击日期的监听
//        calendarView.setListener(new CollapseCalendarView.OnDateSelect() {
//            @Override
//            public void onDateSelected(LocalDate localDate) {
//                int year = localDate.getYear();
//                int month = localDate.getMonthOfYear();
//                int day = localDate.getDayOfMonth();
//                //获取的当前点击的日期
//                L.e(year + "-" + month + "-" + day);
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(year, month - 1, day);
//                String title = new SimpleDateFormat("yyyy 年 MM 月").format(calendar.getTime());
//                calendarView.setMyTitle(title);
//                getData(calendar);
//            }
//        });

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


    private String data;
    private ProgressDialog dialog;

    /**
     * 显示对话框
     */
    private void showProgressDialog() {
        //进度框的工具类
        ProgressUtil.show(MettingRoomActivity.this, "正在加载", false);
    }

    private List<MettingRoomBean> roomBeanList;

    /**
     * 获取会议室的数据
     */
    private void upData(final String date) {

        //显示刷新进度框
        showProgressDialog();
        ////////////////////////////联网获取数据/////////////////////
        RequestParams params = new RequestParams(Const.WuYe.URL_METTING_ROOM);
        params.setConnectTimeout(30 * 1000);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        final String date = sdf.format(calendar.getTime());
        params.addParameter("queryDate", date);
        //选择的日期
        this.data = date;
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null && !result.equals("")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<MettingRoomBean>>() {
                    }.getType();
                    roomBeanList = gson.fromJson(result, type);
                    for (int i = 0; i < roomBeanList.size(); i++) {
                        roomBeanList.get(i).setQueryDate(date);
                    }
                    adapter.setData(roomBeanList);
                    //刷新适配器
                    adapter.notifyDataSetChanged();
                } else {
                    T.showShort(MettingRoomActivity.this, "没有数据");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showShort(MettingRoomActivity.this, "获取数据失败");
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
        MettingRoomBean bean = roomBeanList.get(position);
        bundle.putString("data", data);
        bundle.putSerializable("bean", bean);
        if (bean.getState() != 0) {//0是开放  1未开放
            T.showLong(this, "请选择开放的会议室");
            return;
        }
        openActivty(this, MettingRoomSelectActivity.class, bundle, null);
    }

    @Event(value = R.id.title_back)
    private void back(View view) {
        finish();
    }

    /**
     * 更新数据
     *
     * @param date 日期
     */
//    private void upData(String date) {
//        List<ScheduleInFo> data = DataSupport.where("date=?", date).find(ScheduleInFo.class);
//        roomList = (ArrayList<ScheduleInFo>) data;
//        if (datas.size() == 0) {
//            if (listView.getHeaderViewsCount() > 0) {
//                listView.removeHeaderView(noneView);
//            }
//            listView.addHeaderView(noneView);
//        } else {
//            if (listView.getHeaderViewsCount() > 0) {
//                listView.removeHeaderView(noneView);
//            }
//        }
//        adapter.setDatas(datas);
//    }


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
