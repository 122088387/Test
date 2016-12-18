package com.chaungying.avery_menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/8/16
 */
public class WeekAdapter extends BaseAdapter{

    String[] week = {"一","二","三","四","五","六","日"};

    private Context mContext;

    public WeekAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return week.length;
    }

    @Override
    public Object getItem(int position) {
        return week[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_avery_menu_item,null);
            TextView tv = (TextView) view.findViewById(R.id.menu_item);
            tv.setText(week[position]);
        }else{
            view = convertView;
        }
        return view;
    }
}
