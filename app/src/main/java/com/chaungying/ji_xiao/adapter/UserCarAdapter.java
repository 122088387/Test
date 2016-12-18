package com.chaungying.ji_xiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.ji_xiao.bean.PerCarBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/20
 */
public class UserCarAdapter extends BaseAdapter {

    List<PerCarBean.DataBean> list = new ArrayList<>();

    Context mContext;

    public UserCarAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<PerCarBean.DataBean> list) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_per_user_car_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name_type.setText(list.get(position).getNAME());
        holder.tv_num.setText(list.get(position).getCarSum() + "");
        holder.tv_price.setText(list.get(position).getTotalMoney());
        return convertView;
    }

    class ViewHolder {
        TextView tv_name_type;
        TextView tv_num;
        TextView tv_price;

        ViewHolder(View view) {
            tv_name_type = (TextView) view.findViewById(R.id.tv_name_type);
            tv_num = (TextView) view.findViewById(R.id.tv_num);
            tv_price = (TextView) view.findViewById(R.id.tv_price);

        }
    }
}
