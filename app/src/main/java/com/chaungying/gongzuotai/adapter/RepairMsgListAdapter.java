package com.chaungying.gongzuotai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.gongzuotai.dbbean.RepairBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/15
 */
public class RepairMsgListAdapter extends BaseAdapter {

    private String type = "";

    public void setType(String type) {
        this.type = type;
    }

    private List<RepairBean> list = new ArrayList<>();

    private Context mContext;

    public RepairMsgListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<RepairBean> list) {
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
        RepairBean repairBean = list.get(position);
        int layoutId = repairBean.getLayoutid();
        if (layoutId == 55) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fill_list_item, null);
                holder.item_title = (TextView) convertView.findViewById(R.id.fill_list_item_title);
                holder.item_data = (TextView) convertView.findViewById(R.id.fill_list_item_data);
                holder.item_time = (TextView) convertView.findViewById(R.id.fill_list_item_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (repairBean.isRead() == true) {
                holder.item_title.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_data.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.click_after));
            } else {
                holder.item_title.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.item_data.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.vice_title));
            }
            holder.item_title.setText(repairBean.getTitle());
            holder.item_data.setText(repairBean.getTitle2());
            holder.item_time.setText(repairBean.getTitle1());
            return convertView;
        } else if (layoutId == 56) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fill_list_item1, null);
                holder.item_title = (TextView) convertView.findViewById(R.id.fill_list_item_title);
                holder.item_data = (TextView) convertView.findViewById(R.id.fill_list_item_data);
                holder.item_time = (TextView) convertView.findViewById(R.id.fill_list_item_person);
                //不等于“” 说明是我的保修使用的该适配器
                holder.fill_list_item_type = (ImageView) convertView.findViewById(R.id.fill_list_item_type);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (repairBean.isRead() == true) {
                holder.item_title.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_data.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.click_after));
            } else {
                holder.item_title.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.item_data.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.black));
            }
            holder.item_title.setText(repairBean.getTitle());
            holder.item_data.setText(repairBean.getTitle1());
            holder.item_time.setText(repairBean.getTitle2());

            if (!type.equals("")) {
                holder.fill_list_item_type.setVisibility(View.VISIBLE);
            }
            if (repairBean.getDataType() == 1) {//现场保修
                holder.fill_list_item_type.setImageResource(R.drawable.qiang_dan);
            } else {
                holder.fill_list_item_type.setImageResource(R.drawable.pai_gong);
            }

            return convertView;
        } else if (layoutId == 57) {
            ViewHolder4 holder;
            if (convertView == null) {
                holder = new ViewHolder4();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fill_list_item2, null);
                holder.item_title = (TextView) convertView.findViewById(R.id.fill_list_item_title);
                holder.item_data = (TextView) convertView.findViewById(R.id.fill_list_item_data);
                holder.item_person = (TextView) convertView.findViewById(R.id.fill_list_item_person);
                holder.item_phone = (TextView) convertView.findViewById(R.id.fill_list_item_phone);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder4) convertView.getTag();
            }
            if (repairBean.isRead() == true) {
                holder.item_title.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_data.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_person.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_phone.setTextColor(mContext.getResources().getColor(R.color.click_after));
            }
            holder.item_title.setText(repairBean.getTitle());
            holder.item_data.setText(repairBean.getTitle1());
            holder.item_person.setText(repairBean.getTitle2());
            holder.item_phone.setText(repairBean.getTitle3());
            return convertView;
        }
        return convertView;
    }

    class ViewHolder {
        TextView item_title;
        TextView item_data;
        TextView item_time;
        TextView item_phone;
        ImageView fill_list_item_type;
    }

    class ViewHolder4 {
        TextView item_title;
        TextView item_data;
        TextView item_person;
        TextView item_phone;
    }
}
