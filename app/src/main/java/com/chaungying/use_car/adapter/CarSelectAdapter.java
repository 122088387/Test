package com.chaungying.use_car.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.use_car.bean.CarSelectBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议室预定的适配器
 * @author 王晓赛 or 2016/6/29
 */
public class CarSelectAdapter extends BaseAdapter {

    private Context mContext;
    List<CarSelectBean.DatasBean> list = new ArrayList<>();

    public CarSelectAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<CarSelectBean.DatasBean> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_use_car_select_item,null);
            holder = new ViewHolder();
            holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.tv_select_room_unit = (TextView) convertView.findViewById(R.id.tv_select_room_unit);
            holder.tv_select_person_name = (TextView) convertView.findViewById(R.id.tv_select_person_name);
            holder.iv_select_phone = (ImageView) convertView.findViewById(R.id.iv_select_phone);
            holder.tv_cancel_order = (TextView) convertView.findViewById(R.id.tv_cancel_order);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        CarSelectBean.DatasBean bean = list.get(position);
        //注意：必须将设置控件的值放在if-else的外边
        holder.tv_select_room_unit.setText("单位："+bean.getGroupName());
        holder.tv_order_time.setText(bean.getAppointmentBeginTime()+"-"+bean.getAppointmentEndTime());
        if("任".equals(bean.getUserName())){
            holder.tv_cancel_order.setVisibility(View.VISIBLE);
            holder.iv_select_phone.setVisibility(View.GONE);
            holder.tv_select_person_name.setText("姓名："+bean.getUserName());
        }else{
            holder.tv_cancel_order.setVisibility(View.GONE);
            holder.iv_select_phone.setVisibility(View.VISIBLE);
            holder.tv_select_person_name.setText("姓名："+bean.getUserName());
        }
        holder.iv_select_phone.setOnClickListener(new MyListner(bean));
        holder.tv_cancel_order.setOnClickListener(new MyListner(bean));
        return convertView;
    }
    class MyListner implements View.OnClickListener{

        private CarSelectBean.DatasBean bean;

        public MyListner(CarSelectBean.DatasBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
            if(v instanceof ImageView){
                //打电话进入拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL);
//            intent.setData(Uri.parse("tel:"+bean.getSelectPeronPhone()));
                intent.setData(Uri.parse("tel:"+bean.getPhone()));
                mContext.startActivity(intent);
            }else{
                list.remove(bean);
                notifyDataSetChanged();
            }
        }
    }

    class ViewHolder{
        TextView tv_order_time;
        TextView tv_select_room_unit;
        TextView tv_select_person_name;
        TextView tv_cancel_order;
        ImageView iv_select_phone;
    }
}
