package com.chaungying.address.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.address.adapter.PersonListAdapter;
import com.chaungying.address.bean.PersonListBean;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.metting.view.ProgressUtil;
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
@ContentView(R.layout.activity_person_list)
public class PersonListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    String requestUrl = "";

    @ViewInject(R.id.lv_person_list)
    ListView lv_person_list;

    PersonListBean personListBean;

    PersonListAdapter personListAdapter;

    List<PersonListBean.DataBean> list = new ArrayList<PersonListBean.DataBean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("联系人",R.drawable.nav_return,0);
        requestUrl = getIntent().getStringExtra("requestUrl");

        personListAdapter = new PersonListAdapter(this);
        personListAdapter.setList(list);
        lv_person_list.setAdapter(personListAdapter);
        lv_person_list.setOnItemClickListener(this);
        getData();
    }

    private void getData() {
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_ADDRESS_PARK_LIST + requestUrl);
        String paramsStr = (String) SPUtils.get(this, Const.SpAddress.ADDRESS_KEY, "");
        String[] str = paramsStr.split("-");
        for (int i = 0; i < str.length; i++) {
            String key = str[i].split(":")[0];
            String value = str[i].split(":")[1];
            params.addParameter(key, value);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                personListBean = gson.fromJson(result, PersonListBean.class);
                personListAdapter.setList(personListBean.getData());
                personListAdapter.notifyDataSetChanged();
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
        PersonListBean.DataBean dataBean = personListBean.getData().get(position);
        Intent intent = new Intent(this, PersonDetailsActivity.class);
        intent.putExtra("person_bean", dataBean);
        intent.putExtra("tag", "person_list");
        startActivity(intent);
    }
}
