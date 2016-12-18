package com.chaungying.use_car.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.T;
import com.chaungying.use_car.adapter.CarSelectAdapter;
import com.chaungying.use_car.bean.AllCarBean;
import com.chaungying.use_car.bean.CarSelectBean;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Collections;
import java.util.List;

/**
 * 会议室预订列表界面
 *
 * @author 王晓赛 or 2016/6/29
 */
@ContentView(R.layout.activity_use_car_select)
public class UseCarSelectActivity extends BaseActivity {

    @ViewInject(R.id.lv_car_select)
    private ListView lv_car_select;

    @ViewInject(R.id.title_back)
    private ImageView title_back;

    @ViewInject(R.id.title_menu)
    private TextView title_order;

    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.tv_easy)
    private TextView tv_easy;

    //会议室的适配器
    private CarSelectAdapter adapter;

    //某辆车的预定明细
    private AllCarBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        bean = (AllCarBean) getIntent().getExtras().getSerializable("bean");
        title.setText(bean.getName());
        data = getIntent().getExtras().getString("data");
        getData();
        adapter = new CarSelectAdapter(this);
        lv_car_select.setAdapter(adapter);
    }

    private CarSelectBean selectBean;

    //查询的日期
    static String data;

    private void getData() {
        ////////////////////////////联网获取数据/////////////////////
        RequestParams params = new RequestParams(Const.WuYe.URL_USER_CAR_RECORD);
//        appointmentDate=(查询日期)&carId=车辆id
        params.setConnectTimeout(30 * 1000);
        params.addParameter("appointmentDate", data);
        params.addParameter("carId", bean.getId());
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result != null && !result.equals("")) {
                    Gson gson = new Gson();
                    selectBean = gson.fromJson(result, CarSelectBean.class);
                    //按照时间从小到大排序
                    List<CarSelectBean.DatasBean> list = selectBean.getDatas();
                    if (list.size() == 0) {
                        tv_easy.setVisibility(View.VISIBLE);
                    } else {
                        tv_easy.setVisibility(View.GONE);
                    }
                    Collections.sort(list);
                    adapter.setList(list);
                } else {
                    T.showShort(UseCarSelectActivity.this, "没有数据");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showShort(UseCarSelectActivity.this, "获取数据失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //刷新适配器
                adapter.notifyDataSetChanged();
            }
        });
    }
    //选择时间后判断时间


    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }
    /**
     * 点击预定按钮之后
     *
     * @param view
     */
    @Event(value = R.id.title_menu)
    private void showSelectTime(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        openActivty(UseCarSelectActivity.this, UseCarOrderActivity.class, bundle, null);
    }

    /**
     * 点击返回图片
     *
     * @param view
     */
    @Event(value = R.id.title_back)
    private void titleBack(View view) {
        finish();
    }

}
