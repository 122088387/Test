package com.chaungying.ji_xiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author 种耀淮 在2016年09月08日10:42创建
 */
public class JobPerformanceRootAdapter extends BaseAdapter {

    private Context context;

    private String[] datas;

    public JobPerformanceRootAdapter(Context context, String[] datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(android.R.layout.activity_list_item, parent, false);
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(datas[position]);
        return convertView;
    }
}
