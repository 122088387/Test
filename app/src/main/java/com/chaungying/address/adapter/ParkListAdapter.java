package com.chaungying.address.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.address.bean.GardenContactBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/29
 */
public class ParkListAdapter extends BaseAdapter {


    private Context mContext;
    List<GardenContactBean.DataBean> list = new ArrayList<GardenContactBean.DataBean>();

    public ParkListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<GardenContactBean.DataBean> list) {
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
        ViewHoler holer = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.all_contact_item, null);
            holer = new ViewHoler(convertView);
            convertView.setTag(holer);
        } else {
            holer = (ViewHoler) convertView.getTag();
        }
        holer.parkName.setText(list.get(position).getName());
        return convertView;
    }

    class ViewHoler {
        TextView parkName;

        ViewHoler(View view) {
            parkName = (TextView) view.findViewById(R.id.all_contact_item_park);
        }
    }
}
