package com.chaungying.metting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.metting.bean.MettingRoomRecord;
import com.chaungying.wuye3.R;

import java.util.List;

/**
 * @author 种耀淮 在2016年09月09日10:09创建
 */
public class MettingRoomRecordAdapter extends BaseAdapter {

    private Context mContext;
    private List<MettingRoomRecord.DatasBean> datas;

    public MettingRoomRecordAdapter(Context context, List<MettingRoomRecord.DatasBean> datas) {
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

        holder.title.setText("单位："+datas.get(position).getGroupName());
        holder.content.setText(datas.get(position).getMeetingName());
        String time = datas.get(position).getAppointmentDate() + " " + datas.get(position).getAppointmentBeginTime() + "-" + datas.get(position).getAppointmentEndTime();
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
