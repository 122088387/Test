package com.chaungying.site_repairs.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author 王晓赛 or 2016/6/28
 *         报修记录的适配器
 */
public class RepairRecordAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


//    Context mContext;
//    List<RepairsRecordListBean.ItemsBean> items = new ArrayList<>();
//
//    public RepairRecordAdapter(Context context, List<RepairsRecordListBean.ItemsBean> items) {
//        mContext = context;
//        this.items = items;
//    }
//
//    @Override
//    public int getCount() {
//        return items.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return items.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_repairs_record_item, null);
//            holder = new ViewHolder();
//            holder.tv_task_name = (TextView) convertView.findViewById(R.id.tv_task_name);
//            holder.tv_task_content = (TextView) convertView.findViewById(R.id.tv_task_content);
//            holder.tv_task_address = (TextView) convertView.findViewById(R.id.tv_task_address);
//            holder.tv_task_time = (TextView) convertView.findViewById(R.id.tv_task_order_time);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        RepairsRecordListBean.ItemsBean bean = items.get(position);
//        //注意：必须将设置控件的值放在if-else的外边
//        holder.tv_task_name.setText(bean.getLayoutName1());
//        String key1 = bean.getLayoutValue1();
//        String key2 = bean.getLayoutValue2();
//        String key3 = bean.getLayoutValue3();
//        Class<?> demo = null;
//        Object obj = null;
//        Map<String, Object> map = null;
//        try {
//            demo = Class.forName("com.chaungying.site_repairs.bean.RepairsRecordListBean$RepairsBean");
//            map = new HashMap<String, Object>();
//            obj = items.get(position);
//            Field field = demo.getDeclaredField(key1);
//            field.setAccessible(true);
//            Object o = field.get(obj);
//            map.put(key1, o);
//            field = demo.getDeclaredField(key2);
//            field.setAccessible(true);
//            Object o2 = field.get(obj);
//            map.put(key2, o2);
//            field = demo.getDeclaredField(key3);
//            field.setAccessible(true);
//            Object o3 = field.get(obj);
//            map.put(key3, o3);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            if (entry.getKey().equals(key1)) {
//                holder.tv_task_content.setText(entry.getValue().toString());
//            } else if (entry.getKey().equals(key2)) {
//                holder.tv_task_address.setText(bean.getLayoutName2() + " : " + entry.getValue().toString());
//            } else {
//                holder.tv_task_time.setText(bean.getLayoutName3() + " : " + entry.getValue().toString());
//            }
//        }
//        return convertView;
//    }
//
//    class ViewHolder {
//        TextView tv_task_name;
//        TextView tv_task_content;
//        TextView tv_task_address;
//        TextView tv_task_time;
//    }
}
