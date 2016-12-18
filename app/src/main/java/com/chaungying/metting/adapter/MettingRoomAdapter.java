package com.chaungying.metting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.metting.bean.MettingRoomBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议室预定的适配器
 * @author 王晓赛 or 2016/6/29
 */
public class MettingRoomAdapter extends BaseAdapter{

    private Context mContext;
    List<MettingRoomBean> list = new ArrayList<>();

    public MettingRoomAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<MettingRoomBean> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_metting_room_item,null);
            holder = new ViewHolder();
            holder.metting_name = (TextView) convertView.findViewById(R.id.metting_name);
            holder.metting_person_num = (TextView) convertView.findViewById(R.id.metting_person_num);
            holder.metting_note = (TextView) convertView.findViewById(R.id.metting_note);
            holder.metting_is_order = (ImageView) convertView.findViewById(R.id.iv_is_order);
            holder.open_state = (TextView) convertView.findViewById(R.id.open_state);
            holder.open_time = (TextView) convertView.findViewById(R.id.open_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        MettingRoomBean bean = list.get(position);
        //注意：必须将设置控件的值放在if-else的外边
        holder.metting_name.setText(bean.getName());
        holder.metting_person_num.setText("容纳人数："+bean.getAccommodate()+"人");
        holder.open_time.setText("开放时间："+bean.getBeginTime()+"-"+bean.getEndTime());
        holder.metting_note.setText("描述："+bean.getDesc());
        if(bean.getState()==0){
            holder.open_state.setText("开放");
            holder.open_state.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.open_state.setText("未开放");
            holder.open_state.setTextColor(mContext.getResources().getColor(R.color.list_view_line));
        }
        if(bean.getIsEnable()==0){
            holder.metting_is_order.setImageResource(R.drawable.green);
        }else{
            holder.metting_is_order.setImageResource(R.drawable.red);
        }
        return convertView;
    }
    class ViewHolder{
        TextView metting_name;
        TextView metting_person_num;
        TextView metting_note;
        TextView open_state;
        TextView open_time;
        ImageView metting_is_order;
    }
}
