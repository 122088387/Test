package com.chaungying.ji_xiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.ji_xiao.bean.JobPerCon;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作绩效适配器
 *
 * @author houbopeng 2016.8.17
 */
public class JobPerformanceAdaper extends BaseAdapter {
    private Context context;
    private LayoutInflater lf;
    private List<JobPerCon> jobs;

    public List<JobPerCon> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobPerCon> jobs) {
        this.jobs = jobs;
    }

    public JobPerformanceAdaper(Context context) {
        this.context = context;
        lf = LayoutInflater.from(context);
        jobs = new ArrayList<JobPerCon>();
    }

    @Override
    public int getCount() {
        return jobs.size();
    }

    @Override
    public Object getItem(int position) {
        return jobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lf.inflate(R.layout.job_performance_item, parent, false);
            holder.item1 = (TextView) convertView.findViewById(R.id.item1);
            holder.item2 = (TextView) convertView.findViewById(R.id.item2);
            holder.item3 = (TextView) convertView.findViewById(R.id.item3);
            holder.item4 = (TextView) convertView.findViewById(R.id.item4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item1.setText(jobs.get(position).name);
        holder.item2.setText(jobs.get(position).total + "");
        holder.item3.setText(jobs.get(position).finish + "");
        holder.item4.setText(jobs.get(position).percent);
        if (position == jobs.size() - 1) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.list_view_line));
        }else{
            convertView.setBackgroundColor(context.getResources().getColor(R.color.White));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView item1, item2, item3, item4;
    }
}
