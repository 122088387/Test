package com.chaungying.work_list.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.modues.login.bean.HttpRequestBase;
import com.chaungying.modues.main.bean.RoleAppsBean;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaListActivity;
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
 * @author 王晓赛 or 2016/7/23
 *         展示所有的填报单子列表
 */
@ContentView(R.layout.activity_work_list)
public class WorkListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    List<RoleAppsBean> work_list = new ArrayList<RoleAppsBean>();

    @ViewInject(R.id.lv_work_list)
    private ListView lvWorkList;

    @ViewInject(R.id.title_back)
    private ImageView back;

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar(R.string.work_list, R.drawable.nav_return, 0);
        //获取code值
        RoleAppsBean bean = (RoleAppsBean) getIntent().getExtras().getSerializable("bean");
        String code = bean.getCode();
//        code=(首页菜单的识别码)&userId=（用户有id）
        RequestParams params = new RequestParams(Const.WuYe.URL_WORK_LIST);
        params.setConnectTimeout(5 * 1000);
        params.addParameter("code", code);
        params.addParameter("userId", SPUtils.get(this, Const.SPDate.ID, 4512));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                HttpRequestBase base = gson.fromJson(result, HttpRequestBase.class);
                List<RoleAppsBean> roleApps = base.roleApps;
                work_list = roleApps;
                myAdapter.notifyDataSetChanged();
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
        myAdapter = new MyAdapter();
        lvWorkList.setAdapter(myAdapter);
        lvWorkList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        //根据id进行配置表单
        bundle.putSerializable("bean", work_list.get(position));
        openActivty(this, ZiXunJieDaListActivity.class, bundle, null);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return work_list.size();
        }

        @Override
        public Object getItem(int position) {
            return work_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.activity_work_list_item, null);
                holder.textView = (TextView) convertView.findViewById(R.id.tv_work_list_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(work_list.get(position).getName());
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }

    @Event(value = R.id.title_back)
    private void back(View view) {
        finish();
    }
}
