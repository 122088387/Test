package com.chaungying.gongzuotai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.wuye3.R;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;

import java.util.ArrayList;

/**
 * @author 种耀淮 在2016年07月26日18:49创建
 */
public class WorkAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<WorkItem> datas;

    public WorkAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(ArrayList<WorkItem> datas) {
        this.datas = datas;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_work_list, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.title.setText(datas.get(i).getTitle());
        holder.content.setText(datas.get(i).getContent());
        holder.time.setText(datas.get(i).getTime());
        // 头像
//        if (datas.get(i).getImgUrl() != null && datas.get(i).getImgUrl().length() > 0) {
//            Glide.with(context).load(datas.get(i).getImgUrl())
//                    .error(R.drawable.dingcan).into(holder.avatar);
//        } else {
//            Glide.with(context).load(R.drawable.xianchangbaoxiu).into(holder.avatar);
//        }
        if (i == 0) {
            holder.avatar.setImageResource(R.drawable.dingcan);
        } else {
            holder.avatar.setImageResource(R.drawable.xianchangbaoxiu);
        }
        // 消息计数
        if (datas.get(i).getMsgCount() <= 0) {
            holder.msg.setVisibility(View.GONE);
        } else {
            holder.msg.setVisibility(View.VISIBLE);
            UnreadMsgUtils.show(holder.msg, datas.get(i).getMsgCount());
        }
        if (datas.get(i).getType() == 1) {
            holder.avatar.setImageResource(R.drawable.my_meal);
        } else if (datas.get(i).getType() == 2) {
            holder.avatar.setImageResource(R.drawable.qian_dan);
        } else if (datas.get(i).getType() == 3) {
            holder.avatar.setImageResource(R.drawable.my_repair);
        }
        return view;
    }

    class Holder {
        ImageView avatar;
        TextView title, content, time;
        MsgView msg;

        public Holder(View view) {
            avatar = (ImageView) view.findViewById(R.id.item_workList_avatar);
            title = (TextView) view.findViewById(R.id.item_workList_title);
            content = (TextView) view.findViewById(R.id.item_workList_content);
            time = (TextView) view.findViewById(R.id.item_workList_time);
            msg = (MsgView) view.findViewById(R.id.item_workList_msgView);
        }
    }
}
