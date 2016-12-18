package com.chaungying.use_car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.use_car.bean.AllCarBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 用车管理的适配器
 * @author 王晓赛 or 2016/6/29
 */
public class UseCarAdapter extends BaseAdapter{

    private Context mContext;
    List<AllCarBean> list = new ArrayList<>();

    public UseCarAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<AllCarBean> list) {
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
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_use_car_item,null);
            holder = new ViewHolder();
            holder.car_name = (TextView) convertView.findViewById(R.id.car_name);
            holder.car_accommodate_num = (TextView) convertView.findViewById(R.id.car_accommodate_num);
            holder.car_money = (TextView) convertView.findViewById(R.id.car_money);
            holder.open_time = (TextView) convertView.findViewById(R.id.open_time);
            holder.car_is_order = (ImageView) convertView.findViewById(R.id.iv_is_order);
            holder.open_state = (TextView) convertView.findViewById(R.id.open_state);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AllCarBean bean = list.get(position);
        //注意：必须将设置控件的值放在if-else的外边
        holder.car_name.setText(bean.getName());
        holder.car_accommodate_num.setText("容纳人数："+bean.getAccommodate()+"人");
        holder.car_money.setText("￥"+bean.getCarMoney());
        holder.open_time.setText("可预订时间："+bean.getBeginTime()+"-"+bean.getEndTime());
        if(bean.getIsEnable()==0){
            holder.car_is_order.setImageResource(R.drawable.green);
        }else{
            holder.car_is_order.setImageResource(R.drawable.red);
        }
        if(bean.getState()==0){
            holder.open_state.setText("可预订");
            holder.open_state.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.open_state.setText("不可预定");
            holder.open_state.setTextColor(mContext.getResources().getColor(R.color.list_view_line));
        }
        return convertView;
    }
    class ViewHolder{
        TextView car_name;
        TextView car_accommodate_num;
        TextView car_money;
        TextView open_time;
        ImageView car_is_order;
        TextView open_state;
    }
}
