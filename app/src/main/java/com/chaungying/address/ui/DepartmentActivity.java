package com.chaungying.address.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.address.adapter.DepartmentAdapter;
import com.chaungying.address.bean.GardenContactBean;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/26
 */
@ContentView(R.layout.activity_department)
public class DepartmentActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    String requestUrl = "";

    @ViewInject(R.id.lv_contact)
    ListView listView;

    DepartmentAdapter departmentAdapter;

    List<GardenContactBean.DataBean> list = new ArrayList<GardenContactBean.DataBean>();

    private GardenContactBean gardenContactBean;
    private String parmasStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("部门表",R.drawable.nav_return,0);
        requestUrl = getIntent().getStringExtra("requestUrl");

        //获取第一个界面存起来的key
        parmasStr = (String) SPUtils.get(this, Const.SpAddress.ADDRESS_KEY, "");

        departmentAdapter = new DepartmentAdapter(this);
        departmentAdapter.setList(list);
        listView.setAdapter(departmentAdapter);
        listView.setOnItemClickListener(this);
        getData(requestUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getData(String url) {
        RequestParams params = new RequestParams(Const.WuYe.URL_ADDRESS_PARK_LIST + url);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                gardenContactBean = gson.fromJson(result, GardenContactBean.class);
                departmentAdapter.setList(gardenContactBean.getData());
                departmentAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String isShowMembers = gardenContactBean.getIsShowMembers();
        String isNextUrlParam = gardenContactBean.getIsNextUrlParam();
        String requestUrl = gardenContactBean.getRequestUrl();
        String isShowMembersParam = gardenContactBean.getIsShowMembersParam();
        String paramKey = gardenContactBean.getParamKey();
        int val = gardenContactBean.getData().get(position).getVal();

        if (isShowMembers.equals("1")) {//不是人员列表

            if (isNextUrlParam.equals("1")) {//不拼接

            } else if (isNextUrlParam.equals("0")) {//拼接

            }
            Intent intent = new Intent(this, DepartmentActivity.class);
            intent.putExtra("requestUrl", requestUrl);
            startActivity(intent);
        } else if (isShowMembers.equals("0")) {//是人员列表
            if (isNextUrlParam.equals("1")) {//不拼接

            } else if (isNextUrlParam.equals("0")) {//拼接

            }
            //跳转到人员列表页
            Intent intent = new Intent(this, PersonListActivity.class);
            intent.putExtra("requestUrl", requestUrl);
            startActivity(intent);
        }
        if (isShowMembersParam.equals("0") || isShowMembers.equals("0")) {//存起来
            SPUtils.remove(this, Const.SpAddress.ADDRESS_KEY);
            SPUtils.put(this, Const.SpAddress.ADDRESS_KEY, parmasStr + "-" + paramKey + ":" + val);
        } else if (isShowMembersParam.equals("1")) {//不存

        }
    }
}
