package com.chaungying.use_car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.use_car.bean.UseCarRecord;
import com.chaungying.wuye3.R;

import java.util.List;

/**
 * @author 种耀淮 在2016年09月09日17:32创建
 */
public class UseCarRecordAdapter extends BaseAdapter {


    private Context mContext;
    private List<UseCarRecord.DatasBean> datas;

    public UseCarRecordAdapter(Context context, List<UseCarRecord.DatasBean> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
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
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_metting_room_select_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.title.setText(datas.get(position).getGroupName());
        holder.content.setText(datas.get(position).getCarName());
        String time = datas.get(position).getAppointmentBeginTime() + "-" + datas.get(position).getAppointmentEndTime();
        holder.time.setText(time);

        holder.phone.setVisibility(View.GONE);

        return convertView;
    }

    class Holder {
        private TextView title, content, time;
        private ImageView phone;

        public Holder(View view) {
            title = (TextView) view.findViewById(R.id.tv_select_room_unit);
            content = (TextView) view.findViewById(R.id.tv_select_person_name);
            time = (TextView) view.findViewById(R.id.tv_order_time);
            phone = (ImageView) view.findViewById(R.id.iv_select_phone);
        }

    }
}
