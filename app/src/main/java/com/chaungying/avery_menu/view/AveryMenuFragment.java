package com.chaungying.avery_menu.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaungying.avery_menu.bean.MenuBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/29
 */
public class AveryMenuFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView lv_avery_menu;

    private List<MenuBean.DataBean.CookbookInfosBean> dataBeanList = new ArrayList<MenuBean.DataBean.CookbookInfosBean>();

    public void setDataBeanList(List<MenuBean.DataBean.CookbookInfosBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        adapter.notifyDataSetChanged();
    }

    public static AveryMenuFragment getInstance() {
        AveryMenuFragment sf = new AveryMenuFragment();

        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AveryMenuAdapter(getContext());
    }

    AveryMenuAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.avery_menu_fragment, null);
        lv_avery_menu = (ListView) v.findViewById(R.id.lv_avery_menu_fragment);
        lv_avery_menu.setAdapter(adapter);
//        lv_avery_menu.setOnItemClickListener(this);
        return v;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class AveryMenuAdapter extends BaseAdapter {

        Context mContext;

        public AveryMenuAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return dataBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuBean.DataBean.CookbookInfosBean bean = dataBeanList.get(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.avery_menu_fragment_item, null);
                holder = new ViewHolder();
                holder.tv = (TextView) convertView.findViewById(R.id.tv_avery_menu_name);
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_avery_menu_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //注意：必须将设置控件的值放在if-else的外边
            holder.tv.setText(bean.getInfoValue());
            if (bean.getInfoImage() != null && bean.getInfoImage().length() > 0) {
                Glide.with(mContext).load(bean.getInfoImage()).error(R.drawable.cai_placeholder).into(holder.iv);
            } else {
                Glide.with(mContext).load(R.drawable.cai_placeholder).into(holder.iv);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView iv;
            TextView tv;
        }
    }
}
