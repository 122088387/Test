package com.chaungying.gongzuotai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.gongzuotai.dbbean.OrderMealBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/15
 */
public class MealMsgListAdapter extends BaseAdapter{

    private List<OrderMealBean> list = new ArrayList<>();

    private Context mContext;

    public MealMsgListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<OrderMealBean> list) {
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
        OrderMealBean orderMealBean =  list.get(position);
        int layoutId = orderMealBean.getLayoutid();
        if(layoutId == 55){
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
            if(orderMealBean.isRead()){
                holder.item_title.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_data.setTextColor(mContext.getResources().getColor(R.color.click_after) );
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.click_after) );
            }else{
                holder.item_title.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.item_data.setTextColor(mContext.getResources().getColor(R.color.colorPrimary) );
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.vice_title) );
            }
            holder.item_title.setText(orderMealBean.getTitle());
            holder.item_data.setText(orderMealBean.getTitle2() );
            holder.item_time.setText(orderMealBean.getTitle1() );
            return convertView;
        }else if(layoutId == 56){
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fill_list_item1, null);
                holder.item_title = (TextView) convertView.findViewById(R.id.fill_list_item_title);
                holder.item_data = (TextView) convertView.findViewById(R.id.fill_list_item_data);
                holder.item_time = (TextView) convertView.findViewById(R.id.fill_list_item_person);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if(orderMealBean.isRead() == true){
                holder.item_title.setTextColor(mContext.getResources().getColor(R.color.click_after));
                holder.item_data.setTextColor(mContext.getResources().getColor(R.color.click_after) );
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.click_after) );
            }
            holder.item_title.setText(orderMealBean.getTitle());
            holder.item_data.setText(orderMealBean.getTitle1() );
            holder.item_time.setText(orderMealBean.getTitle2() );
            return convertView;
        }
        else if(layoutId == 57){
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
            holder.item_title.setText(orderMealBean.getTitle());
            holder.item_data.setText(orderMealBean.getTitle2() );
            holder.item_person.setText(orderMealBean.getTitle1() );
            holder.item_phone.setText(orderMealBean.getTitle3());
            return convertView;
        }
        return null;
    }
    class ViewHolder {
        TextView item_title;
        TextView item_data;
        TextView item_time;
        TextView item_phone;
    }
    class ViewHolder4{
        TextView item_title;
        TextView item_data;
        TextView item_person;
        TextView item_phone;
    }
}
