package com.chaungying.address.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chaungying.address.bean.PersonListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/29
 */
public class OftenContactAdapter extends BaseAdapter {


    private Context mContext;
    List<PersonListBean.DataBean> list = new ArrayList<PersonListBean.DataBean>();

    public OftenContactAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<PersonListBean.DataBean> list) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
