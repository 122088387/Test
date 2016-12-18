package com.chaungying.jinjitonggao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.bean.SubtitlesItem;
import com.chaungying.jinjitonggao.bean.TimeSelectItemBean;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/7/19
 */
@ContentView(R.layout.activity_time_select)
public class TimeSelectActivity extends BaseActivity {

    ArrayList<TimeSelectItemBean> timeList = new ArrayList<TimeSelectItemBean>();

    @ViewInject(R.id.title_back)
    private ImageView iv_back;

    @ViewInject(R.id.lv_time_select)
    private ListView lv_time_select;

    private ArrayList<SubtitlesItem> list = new ArrayList<SubtitlesItem>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //设置title Bar
        setActionBar(R.string.time_select, R.drawable.nav_return, 0);

        list = (ArrayList<SubtitlesItem>) getIntent().getSerializableExtra("time_list");

        for (int i = 0; i < list.size(); i++) {
            TimeSelectItemBean bean = new TimeSelectItemBean();
            bean.setTime(list.get(i).getName());
            timeList.add(bean);
        }

        MyAdapter adapter = new MyAdapter();
        lv_time_select.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return timeList.size();
        }

        @Override
        public Object getItem(int position) {
            return timeList.get(position);
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
                convertView = getLayoutInflater().inflate(R.layout.activity_time_select_item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_time_select_item);
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_time_select);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(timeList.get(position).getTime());
            if(timeList.get(position).isFull()){
                holder.iv.setImageResource(R.drawable.red);
            }else{
                holder.iv.setImageResource(R.drawable.green);
            }
            return convertView;
        }
        class ViewHolder {
            TextView tv;
            ImageView iv;
        }
    }


    @Event(value = R.id.title_back)
    private void IvBack(View view) {
        finish();
    }
}
