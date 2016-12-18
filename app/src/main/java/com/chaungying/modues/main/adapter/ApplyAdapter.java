package com.chaungying.modues.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.modues.main.bean.RoleAppsBean;
import com.chaungying.wuye3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor 王晓赛 or 2016/6/22
 */
public class ApplyAdapter extends BaseAdapter {

    private List<RoleAppsBean> data_list = new ArrayList<>();
    private Context mContext;


    public ApplyAdapter(Context context, List<RoleAppsBean> data_list) {
        mContext = context;
        this.data_list = data_list;
    }

    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return data_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.apply_button_item, null);
            TextView tv = (TextView) view.findViewById(R.id.text);
            final ImageView iv = (ImageView) view.findViewById(R.id.image);
            tv.setText(data_list.get(position).getName());
//            Glide.with(mContext)
//                    .load(data_list.get(position).getIcoUrl())
//                    .asBitmap().fitCenter().into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    iv.setImageBitmap(resource);
//                }
//            });
            if(data_list.get(position).getIcoUrl() != null && !data_list.get(position).getIcoUrl().equals("")){
                Picasso.with(mContext).load(data_list.get(position).getIcoUrl()).into(iv);
            }
//            Glide.with(mContext).load(data_list.get(position)).into(iv);
        }
        return view;
    }

}
