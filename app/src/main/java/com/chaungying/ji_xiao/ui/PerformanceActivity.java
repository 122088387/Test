package com.chaungying.ji_xiao.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.ji_xiao.bean.PerformanceBean;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/19
 */

@ContentView(R.layout.activity_performance)
public class PerformanceActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    List<PerformanceBean> performanceBeanList = new ArrayList<PerformanceBean>();

    @ViewInject(R.id.lv_work_list)
    private ListView lvWorkList;

    @ViewInject(R.id.title_back)
    private ImageView back;

    MyAdapter myAdapter;

    String[] titleStr = {"签到分析", "维修派工", "投诉咨询", "用车情况"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("工作绩效", R.drawable.nav_return, 0);

        for (int i = 0; i < titleStr.length; i++) {
            PerformanceBean bean = new PerformanceBean();
            bean.setTitle(titleStr[i]);
            performanceBeanList.add(bean);
        }

        myAdapter = new MyAdapter();
        lvWorkList.setAdapter(myAdapter);
        lvWorkList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://签到
                openActivty(this, PerSignActivity.class, null, null);
                break;
            case 1://维修派工
                openActivty(this, PerRepairDispatchActivity.class, null, null);
                break;
            case 2://投诉咨询
                openActivty(this, PerComplaintsConsultActivity.class, null, null);
                break;
            case 3://用车
                openActivty(this, PerUserCarActivity.class, null, null);
                break;
        }

    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return performanceBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return performanceBeanList.get(position);
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
            holder.textView.setText(performanceBeanList.get(position).getTitle());
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
