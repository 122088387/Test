package com.chaungying.park_navigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.park_navigation.bean.ParkListBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/15
 */
public class ParkListAdapter extends BaseAdapter {

    private List<ParkListBean.DataBean> list = new ArrayList<>();

    private Context mContext;

    public ParkListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<ParkListBean.DataBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParkListBean.DataBean parkListBean = list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_park_list_item, null);
            holder.item_title = (TextView) convertView.findViewById(R.id.tv_park_list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_title.setText(parkListBean.getName());
        return convertView;
    }

    class ViewHolder {
        TextView item_title;
    }
}
