package com.chaungying.news.adapter;

/**
 * @author 种耀淮 在2016年08月04日12:03创建
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.news.bean.NoticeItem;
import com.chaungying.wuye3.R;

import java.util.ArrayList;

/**
 * @author 种耀淮 在2016年07月26日19:35创建
 */
public class NoticeListAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<NoticeItem> datas = new ArrayList<>();

    public NoticeListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(ArrayList<NoticeItem> data) {
        datas = data;
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setDate(datas.get(i).getCreatTime());
            if (i == 0) {
                datas.get(i).setDisPlayDate("error");
            } else {
                datas.get(i).setDisPlayDate(datas.get(i - 1).getCreatTime());
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_notice_list, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        // 标题
        holder.title.setText(datas.get(i).getActivityName());
        // 标题
        if (datas.get(i).getState() == 1) {//未读
            holder.title.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.title.setTextColor(context.getResources().getColor(R.color.gray));
        }
        // 来源
        holder.source.setText("来源：" + datas.get(i).getSource());
        // 评论
        if (datas.get(i).getRemarks().length() > 0) {
            holder.comments.setText(datas.get(i).getRemarks() + "条评论");
        } else {
            holder.comments.setText("0条评论");
        }
        // 是否显示日期条
//        if (datas.get(i).isDisPlayDate()) {
//            holder.topLabel.setVisibility(View.VISIBLE);
//            holder.topLabel.setText(datas.get(i).getCreatTime());
//        } else {
//            holder.topLabel.setVisibility(View.GONE);
//        }

        return view;
    }

    class Holder {
        TextView title, source, comments, topLabel;

        public Holder(View view) {
            title = (TextView) view.findViewById(R.id.item_noticeList_title);
            source = (TextView) view.findViewById(R.id.item_noticeList_source);
            comments = (TextView) view.findViewById(R.id.item_noticeList_comments);
            topLabel = (TextView) view.findViewById(R.id.item_noticeList_topLabel);
        }
    }
}
