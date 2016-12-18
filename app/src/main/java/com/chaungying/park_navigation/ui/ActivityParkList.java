package com.chaungying.park_navigation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.park_navigation.adapter.ParkListAdapter;
import com.chaungying.park_navigation.bean.ParkListBean;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author 王晓赛 or 2016/8/16
 */
@ContentView(R.layout.activity_park_list)
public class ActivityParkList extends BaseActivity implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.lv_park_list)
    ListView lv_park_list;

    ParkListAdapter parkListAdapter;

    List<ParkListBean.DataBean> dataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("园区导航",R.drawable.nav_return,0);
        parkListAdapter = new ParkListAdapter(this);
        lv_park_list.setAdapter(parkListAdapter);
        initData();
        lv_park_list.setOnItemClickListener(this);
    }

    private void initData() {
        ProgressUtil.show(this, "加载中...");
//        row=1&page=1
        RequestParams params = new RequestParams(Const.WuYe.URL_PARK_LIST);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ParkListBean bean = gson.fromJson(result, ParkListBean.class);
                dataBeanList = bean.getData();
                parkListAdapter.setList(dataBeanList);
                parkListAdapter.notifyDataSetChanged();
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ParkListBean.DataBean bean = dataBeanList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("park_bean", bean);
        openActivty(this, ActivityParkNavigation.class, bundle, null);
    }
}
