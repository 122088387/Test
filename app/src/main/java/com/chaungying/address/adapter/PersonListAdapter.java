package com.chaungying.address.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.address.bean.PersonListBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/31
 */
public class PersonListAdapter extends BaseAdapter {

    private Context mContext;
    List<PersonListBean.DataBean> list = new ArrayList<PersonListBean.DataBean>();

    public PersonListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<PersonListBean.DataBean> list) {
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
        ViewHoler holer = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_person_list_item, null);
            holer = new ViewHoler(convertView);
            convertView.setTag(holer);
        } else {
            holer = (ViewHoler) convertView.getTag();
        }
        holer.person_list_iv.setImageResource(R.drawable.person_touxiang);
        holer.person_list_name.setText(list.get(position).getUserName());
        holer.person_list_position.setText(list.get(position).getPosition());
        return convertView;
    }

    class ViewHoler {
        ImageView person_list_iv;
        TextView person_list_name;
        TextView person_list_position;


        ViewHoler(View view) {
            person_list_iv = (ImageView) view.findViewById(R.id.person_list_iv);
            person_list_name = (TextView) view.findViewById(R.id.person_list_name);
            person_list_position = (TextView) view.findViewById(R.id.person_list_position);
        }
    }


}
