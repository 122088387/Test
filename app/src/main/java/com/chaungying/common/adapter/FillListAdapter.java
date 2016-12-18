package com.chaungying.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.common.bean.FillListBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/3
 *         <p/>
 *         填报单子的适配器
 */
public class FillListAdapter extends BaseAdapter {

    private Context mContext;

    private FillListBean fillListBeen;

    private List<List<FillListBean.DataBean>> list = new ArrayList<>();

    public FillListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<List<FillListBean.DataBean>> list) {
        this.list = list;
    }

    public void setFillListBeen(FillListBean fillListBeen) {
        this.fillListBeen = fillListBeen;
        list.clear();
        list.addAll(fillListBeen.getData());
    }
    private int layoutId;

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
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
        if (layoutId == 55) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fill_list_item, null);
                holder.item_title = (TextView) convertView.findViewById(R.id.fill_list_item_title);
                holder.item_data = (TextView) convertView.findViewById(R.id.fill_list_item_data);
                holder.item_person = (TextView) convertView.findViewById(R.id.fill_list_item_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ArrayList<FillListBean.DataBean> itemList = (ArrayList<FillListBean.DataBean>) list.get(position);
            for (int i = 0; i < itemList.size(); i++) {
                if (i == 0) {
                    if (itemList.get(0).getIsShowTitle() == 0) {
                        holder.item_title.setText(itemList.get(0).getTitle() + ":" + itemList.get(0).getValue());
                    } else {
                        holder.item_title.setText(itemList.get(0).getValue());
                    }
                } else if (i == 1) {
                    if (itemList.get(1).getIsShowTitle() == 0) {
                        holder.item_data.setText(itemList.get(1).getTitle() + ":" + itemList.get(1).getValue());
                    } else {
                        holder.item_data.setText(itemList.get(1).getValue());
                    }
                } else if (i == 2) {
                    if (itemList.get(2).getIsShowTitle() == 0) {
                        holder.item_person.setText(itemList.get(2).getTitle() + ":" + itemList.get(2).getValue());
                    } else {
                        holder.item_person.setText(itemList.get(2).getValue());
                    }
                }
            }
            return convertView;
        } else if (layoutId == 56) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fill_list_item1, null);
                holder.item_title = (TextView) convertView.findViewById(R.id.fill_list_item_title);
                holder.item_data = (TextView) convertView.findViewById(R.id.fill_list_item_data);
                holder.item_person = (TextView) convertView.findViewById(R.id.fill_list_item_person);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ArrayList<FillListBean.DataBean> itemList = (ArrayList<FillListBean.DataBean>) list.get(position);
            for (int i = 0; i < itemList.size(); i++) {
                if (i == 0) {
                    if (itemList.get(0).getIsShowTitle() == 0) {
                        holder.item_title.setText(itemList.get(0).getTitle() + ":" + itemList.get(0).getValue());
                    } else {
                        holder.item_title.setText(itemList.get(0).getValue());
                    }
                } else if (i == 1) {
                    if (itemList.get(1).getIsShowTitle() == 0) {
                        holder.item_data.setText(itemList.get(1).getTitle() + ":" + itemList.get(1).getValue());
                    } else {
                        holder.item_data.setText(itemList.get(1).getValue());
                    }
                } else if (i == 2) {
                    if (itemList.get(2).getIsShowTitle() == 0) {
                        holder.item_person.setText(itemList.get(2).getTitle() + ":" + itemList.get(2).getValue());
                    } else {
                        holder.item_person.setText(itemList.get(2).getValue());
                    }
                }
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
            ArrayList<FillListBean.DataBean> itemList = (ArrayList<FillListBean.DataBean>) list.get(position);
            for (int i = 0; i < itemList.size(); i++) {
                if (i == 0) {
                    if (itemList.get(0).getIsShowTitle() == 0) {
                        holder.item_title.setText(itemList.get(0).getTitle() + ":" + itemList.get(0).getValue());
                    } else {
                        holder.item_title.setText(itemList.get(0).getValue());
                    }
                } else if (i == 1) {
                    if (itemList.get(1).getIsShowTitle() == 0) {
                        holder.item_data.setText(itemList.get(1).getTitle() + ":" + itemList.get(1).getValue());
                    } else {
                        holder.item_data.setText(itemList.get(1).getValue());
                    }
                } else if (i == 2) {
                    if (itemList.get(2).getIsShowTitle() == 0) {
                        holder.item_person.setText(itemList.get(2).getTitle() + ":" + itemList.get(2).getValue());
                    } else {
                        holder.item_person.setText(itemList.get(2).getValue());
                    }
                } else if (i == 3) {
                    if (itemList.get(2).getIsShowTitle() == 0) {
                        holder.item_phone.setText(itemList.get(3).getTitle() + ":" + itemList.get(3).getValue());
                    } else {
                        holder.item_phone.setText(itemList.get(3).getValue());
                    }
                }
            }
            return convertView;
        }
        return convertView;
    }

    class ViewHolder {
        TextView item_title;
        TextView item_data;
        TextView item_person;
    }

    class ViewHolder4 {
        TextView item_title;
        TextView item_data;
        TextView item_person;
        TextView item_phone;
    }
}
